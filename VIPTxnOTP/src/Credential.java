import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import com.verisign.www._2006._08.vipservice.*;

public class Credential {
	VipSoapInterfaceService service;
	VipSoapInterface port;
	String m_url;
	String version = "2.0";
	String nonce = "abcd1234";// unique per transaction – can use unique identifiers for troubleshooting
	String certFile = "/home/vipCertExp12292016.p12"; // replace with path to your certificate file
	String password = "Password1"; // replace with the password for your certificate

	public Credential(String url)
	{
		try{
			service =new VipSoapInterfaceServiceLocator();
			m_url = url;
			System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
			System.setProperty("javax.net.ssl.keyStore", certFile);
			System.setProperty("javax.net.ssl.keyStorePassword", password);
		}
		catch (Exception e)
		{
			System.out.println("Exception : " + e);
		}
	}

	public void getServerTime()
	{
		try {
			port = service.getvipServiceAPI(new java.net.URL(m_url+"/mgmt/soap"));
			GetServerTimeType x = new GetServerTimeType(version,nonce);
			GetServerTimeResponseType resp = port.getServerTime(x);
			BigInteger reason = new BigInteger(resp.getStatus().getReasonCode());
			if (reason.intValue() != 0){
				System.out.println(" Message = " + resp.getStatus().getStatusMessage());
				System.out.println(" Error Detail = " + resp.getStatus().getErrorDetail());
			}else{
				System.out.println(" Result = " + resp.getStatus().getStatusMessage());
				System.out.println(" Server Time = " + resp.getTimestamp().getTime().toString());
			}
		} catch (Exception e) {
			System.out.println(" getServerTime(), Exception : " + e);
		}
	}

	public String sendTxnOTP(String phoneId, String type) throws Exception {
		try {
			port = service.getvipServiceAPI(new java.net.URL(m_url + "/txn/soap"));			
			DestinationType m = new DestinationType(phoneId);
			SMSDeliveryInfoType t = null;

			if (type.equals("SMS")){
				// set destination type for SMS
				m.setType(TokenType.SMS);
				// for SMS we can define the message text in real-time
				t = new SMSDeliveryInfoType(
						"46847",
						"This is a verification code from Bank of America: _OTP_");
			} else if (type.equals("Voice")){
				// set destination type for Voice
				m.setType(TokenType.Voice);
				//for Voice, the voice template needs to be recorded, and then referenced
				//using the default template for now
			} else {
				System.out.println("sendTxnOTP to Phone : " + phoneId + " : via " + type + " : Failed! \n");
				System.out.println("Message : Invalid Type!");
				return null;
			}

			DeliverTxnOTPType x = new DeliverTxnOTPType(
					version, 
					nonce, 
					null,
					null,
					m,
					t, 
					null);

			DeliverTxnOTPResponseType resp = port.deliverTxnOTP(x);

			BigInteger reason = new BigInteger(resp.getStatus().getReasonCode());
			if (reason.intValue() != 0) {
				System.out.println("sendTxnOTP to Phone : " + phoneId + " : via " + type + " : Failed! \n");

				System.out.println("Reason Code = " + reason.toString(16));
				System.out.println("Message : " + resp.getStatus().getStatusMessage());
				System.out.println("Error Detail : " + resp.getStatus().getErrorDetail());   
				return null;
			} else {
				System.out.println("sendTxnOTP to Phone : " + phoneId + " : via " + type + " : Success! \n");
				System.out.println("sendTxnOTP Transaction ID : " + resp.getTxnId() + "\n");
				return resp.getTxnId();
			}
		} catch (Exception e) {
			System.out.println("sendTxnOTP() Exception : " + e);
			throw e;
		}
	}
	
	/**
	 * Method to send a voice transaction verification request. 
	 * Verification OTP is returned for display.
	 * The request is submitted and polled for success/failure.
	 * @param
	 * @return txnOTP, txnId
	 */
	public String[] sendTxnVerif(String phoneId, String account, String amount, String fraction) throws Exception {
		String voiceTemplate = new String("PaymentVerify");
		String language = new String("en-us");
		String currency = new String("USD");
		String[] txnDetails = new String[2];
		txnDetails[0] = null;
		txnDetails[1] = null;

		port = service.getvipServiceAPI(new java.net.URL(m_url + "/txn/soap"));

		NameValuePairType accountEnd = new NameValuePairType(account);
		accountEnd.setName("accountEndsWith");

		NameValuePairType amountInteger = new NameValuePairType(amount);
		amountInteger.setName("amount");        

		NameValuePairType amountFraction = new NameValuePairType(fraction);
		amountFraction.setName("fraction");

		NameValuePairType currencyName = new NameValuePairType(currency);
		currencyName.setName("currency");

		NameValuePairType[] params = new NameValuePairType[4];
		params[0] = accountEnd;
		params[1] = amountInteger;
		params[2] = amountFraction;
		params[3] = currencyName;

		try {			
			SubmitTxnVerificationType tx = new SubmitTxnVerificationType(
					version,
					nonce, 
					null, 
					phoneId,
					null,
					language,
					voiceTemplate,
					params
			);
			SubmitTxnVerificationResponseType resp = port.submitTxnVerification(tx);
			BigInteger reason = new BigInteger(resp.getStatus().getReasonCode());
			if (reason.intValue() != 0) {
				System.out.println("sendTxnVerificaton URL = " + m_url);
				System.out.println("sendTxnVerification to Phone = " + phoneId + ": Failed");
				System.out.println("Reason Code = " + reason.toString(16));
				System.out.println("Message = " + resp.getStatus().getStatusMessage());
				System.out.println("Error Detail = " + resp.getStatus().getErrorDetail());
			} else {
				System.out.println("SendTxnVerificaton URL = " + m_url);
				System.out.println("SendTxnVerification to Phone = " + phoneId + ": Success");
				txnDetails[0] = resp.getTxnOTP();
				txnDetails[1] = resp.getTxnId();
			}
		} catch (Exception e) {
			System.out.println("SendTxnVerification() Exception: " + e);
			throw e;
		} 
		return txnDetails;
	}
	
	public void pollTxn(String id) throws Exception {
		port = service.getvipServiceAPI(new java.net.URL(m_url + "/txn/soap"));
		
		try {			
			PollTxnVerificationType txv = new PollTxnVerificationType(
					version,
					nonce, 
					null, 
					id
			);
			PollTxnVerificationResponseType resp = port.pollTxnVerification(txv);
			BigInteger reason = new BigInteger(resp.getStatus().getReasonCode());
			if (reason.intValue() != 0) {
				System.out.println("pollTxn URL = " + m_url);
				System.out.println("pollTxn for transaction = " + id + ": Failed");
				System.out.println("Reason Code = " + reason.toString(16));
				System.out.println("Message = " + resp.getStatus().getStatusMessage());
				System.out.println("Error Detail = " + resp.getStatus().getErrorDetail());
			} else {
				System.out.println("pollTxn URL = " + m_url);
				System.out.println("pollTxn for transaction = " + id + ": Success");
			}
		} catch (Exception e) {
			System.out.println("pollTxnVerif() Exception: " + e);
			throw e;
		} 
	}
	
	public boolean verifTxnOTP(String txnId, String otp) throws Exception {
		try {
			port = service.getvipServiceAPI(new java.net.URL(m_url + "/txn/soap"));
			VerifyTxnOTPType x = new VerifyTxnOTPType(
					version, 
					nonce, 
					null,
					txnId,
					otp);

			VerifyTxnOTPResponseType resp = port.verifyTxnOTP(x);

			BigInteger reason = new BigInteger(resp.getStatus().getReasonCode());
			if (reason.intValue() != 0) {
				System.out.println("verifTxnOTP for Transaction ID : " + txnId + " : Failed! \n");

				System.out.println("Reason Code = " + reason.toString(16));
				System.out.println("Message = " + resp.getStatus().getStatusMessage());
				System.out.println("Error Detail = " + resp.getStatus().getErrorDetail());
				/*
				System.out.println("Checking Transaction Status!");
				
				try {
					pollTxn(txnId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				*/
				return false;
			} else {
				System.out.println("verifTxnOTP for Transaction ID : " + txnId + " : Success! \n");        
				return true;
			}
		} catch (Exception e) {
			System.out.println("verifTxnOTP() Exception : " + e);
			throw e;
		}
	}

	public static String readOTP() {
		System.out.print("Enter the OTP and press Enter: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String otp = null;

		try 
		{
			otp = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.print("\n");
		return otp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "https://services-auth.vip.symantec.com";
		String phoneNo = "15555555555"; //your phone number here
		String type = "Voice"; //use either "SMS" or "Voice" 
		String txnID = null;
		String OTP = null;
		
		String whole = "1000";
		String fraction = "50";
		String acct = "1234";
		String[] txn = new String[2];
		txn[0] = null;
		txn[1] = null;
		
		Credential c = new Credential(url);

		System.out.println("\n Result of getServerTime : \n");
		c.getServerTime();

		System.out.println("\n Result of sendTxnOTP : \n");

		try {
			txnID = c.sendTxnOTP(phoneNo, type);	
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		OTP = readOTP();
		try {
			c.verifTxnOTP(txnID, OTP);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			txn = c.sendTxnVerif(phoneNo, acct, whole, fraction);	
			System.out.println("sendTxnVerif OTP = " + txn[0]);
			System.out.println("sendTxnVerif ID  = " + txn[1]);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		OTP = readOTP();
		
		try {
			c.pollTxn(txn[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
