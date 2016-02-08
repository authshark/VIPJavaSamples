import java.rmi.RemoteException;
import com.symantec.vipuserservices.wsclient.AuthenticationServiceStub;
import com.symantec.vipuserservices.wsclient.AuthenticationServiceStub.*;

public class VipUserServicesAuthClient {
public static void main(String[] args) throws RemoteException {
	
	System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
	System.setProperty("javax.net.ssl.keyStore", "C:\\home\\alin\\vip_cert.p12");
	System.setProperty("javax.net.ssl.keyStorePassword", "Password1");
	
	AuthenticationServiceStub authenticationServiceStub = new AuthenticationServiceStub("https://userservices-auth.vip.symantec.com/vipuserservices/AuthenticationService_1_1");
	
	AuthenticateUserWithPushRequest authenticateUserWithPushRequestBean = new AuthenticateUserWithPushRequest();
	AuthenticateUserWithPushRequestType authenticateUserWithPushRequest = new AuthenticateUserWithPushRequestType();
	
	//add requestId to the request
	RequestIdType requestIdType = new RequestIdType();
	requestIdType.setRequestIdType("rqstId" + System.currentTimeMillis());
	
	authenticateUserWithPushRequest.setRequestId(requestIdType);
	
	//add userId to the request; 
	UserIdType userIdType = new UserIdType();
	userIdType.setUserIdType("alin");
	
	authenticateUserWithPushRequest.setUserId(userIdType);
	
	//add pushAuthData to the request
	PushAuthDataType pushAuthData = new PushAuthDataType();
	
	//first, add the request KeyValue pair to pushAuthData
	//set the request timeout KeyValue pair and add it to pushAuthData
	KeyType pushRequestKey = new KeyType();
	pushRequestKey.setKeyType("request.timeout");
		
	ValueType pushRequestValue = new ValueType();
	pushRequestValue.setValueType("120");
		
	KeyValuePairType pushRequestTimeout = new KeyValuePairType();
	pushRequestTimeout.setKey(pushRequestKey);
	pushRequestTimeout.setValue(pushRequestValue);
	
	pushAuthData.addRequestParameters(pushRequestTimeout);
	
	//now add all display KeyValue pairs to psuhAuthData: message, title, text and profile 
	//set the display message KeyValue pair and add it to pushAuthData
	KeyType pushDisplayMessageKey = new KeyType();
	pushDisplayMessageKey.setKeyType("push.message.text");
	
	ValueType pushDisplayMessageValue = new ValueType();
	pushDisplayMessageValue.setValueType("Myles is asking you to confirm a Login request!");
	
	KeyValuePairType pushDisplayMessage = new KeyValuePairType();
	pushDisplayMessage.setKey(pushDisplayMessageKey);
	pushDisplayMessage.setValue(pushDisplayMessageValue);
	
	pushAuthData.addDisplayParameters(pushDisplayMessage);
		
	
	//set the display title KeyValue pair and add it to pushAuthData
	KeyType pushDisplayTitleKey = new KeyType();
	pushDisplayTitleKey.setKeyType("display.message.title");
	
	ValueType pushDisplayTitleValue = new ValueType();
	pushDisplayTitleValue.setValueType("VIP Login from Myles");
	
	KeyValuePairType pushDisplayTitle = new KeyValuePairType();
	pushDisplayTitle.setKey(pushDisplayTitleKey);
	pushDisplayTitle.setValue(pushDisplayTitleValue);
	
	pushAuthData.addDisplayParameters(pushDisplayTitle);
	
	//set the display text KeyValue pair and add it to pushAuthData
	KeyType pushDisplayTextKey = new KeyType();
	pushDisplayTextKey.setKeyType("display.message.text");
	
	ValueType pushDisplayTextValue = new ValueType();
	pushDisplayTextValue.setValueType("Please confirm access to Myles's website at the URL below.");
	
	KeyValuePairType pushDisplayText = new KeyValuePairType();
	pushDisplayText.setKey(pushDisplayTextKey);
	pushDisplayText.setValue(pushDisplayTextValue);
	
	pushAuthData.addDisplayParameters(pushDisplayText);
	
	//set the display profile KeyValue pair and add it to pushAuthData
	KeyType pushDisplayProfileKey = new KeyType();
	pushDisplayProfileKey.setKeyType("display.message.profile");
	
	ValueType pushDisplayProfileValue = new ValueType();
	pushDisplayProfileValue.setValueType("https://www.rockstarmyles.com/");
	
	KeyValuePairType pushDisplayProfile = new KeyValuePairType();
	pushDisplayProfile.setKey(pushDisplayProfileKey);
	pushDisplayProfile.setValue(pushDisplayProfileValue);
	
	pushAuthData.addDisplayParameters(pushDisplayProfile);	
			
	//add pushAuthData to the request
	authenticateUserWithPushRequest.setPushAuthData(pushAuthData);
	
	authenticateUserWithPushRequestBean.setAuthenticateUserWithPushRequest(authenticateUserWithPushRequest);
	
	//send the request	
	AuthenticateUserWithPushResponse authenticateUserWithPushResponseBean = authenticationServiceStub.authenticateUserWithPush(authenticateUserWithPushRequestBean);
	AuthenticateUserWithPushResponseType authenticateUserWithPushResponse = authenticateUserWithPushResponseBean.getAuthenticateUserWithPushResponse();
	
	System.out.println("Status : " + authenticateUserWithPushResponse.getStatus());
	System.out.println("Status message : " + authenticateUserWithPushResponse.getStatusMessage());
	System.out.println("Transaction ID : " + authenticateUserWithPushResponse.getTransactionId());
	
	//ignore this for now
	//TransactionIdType transactionIdType = new TransactionIdType();
	//transactionIdType.setTransactionIdType(authenticateUserWithPushResponse.getTransactionId());	
}	
}