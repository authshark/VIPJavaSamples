import java.rmi.RemoteException;

import com.verisign.vipuser.QueryServiceStub;
import com.verisign.vipuser.QueryServiceStub.*;
// import com.verisign.vipuser.QueryServiceStub.GetServerTimeRequest;
//import com.verisign.vipuserservices.wsclient.QueryServiceStub.GetServerTimeRequestType;
//import com.verisign.vipuserservices.wsclient.QueryServiceStub.GetServerTimeResponse;
//import com.verisign.vipuserservices.wsclient.QueryServiceStub.GetServerTimeResponseType;
//import com.verisign.vipuserservices.wsclient.QueryServiceStub.RequestIdType;

public class VipUserServicesQueryClient {
	public static void main(String[] args) throws RemoteException {
		String pathToP12File = "C:\\home\\prodSE.p12";
		String password = "password1";
		System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
		System.setProperty("javax.net.ssl.keyStore", pathToP12File);
		System.setProperty("javax.net.ssl.keyStorePassword", password);
		QueryServiceStub queryServiceStub = new QueryServiceStub("https://vipuserservices-auth.verisign.com/vipuserservices/QueryService_1_0");
		GetServerTimeRequest getServerTimeRequestBean = new GetServerTimeRequest();
		GetServerTimeRequestType getServerTimeRequest = new GetServerTimeRequestType();

		getServerTimeRequestBean.setGetServerTimeRequest(getServerTimeRequest);
		RequestIdType requestIdType = new RequestIdType();
		requestIdType.setRequestIdType("rqstId" + System.currentTimeMillis());
		getServerTimeRequest.setRequestId(requestIdType);
		GetServerTimeResponse getServerTimeResponseBean = 
			queryServiceStub.getServerTime(getServerTimeRequestBean);
		GetServerTimeResponseType getServerTimeResponse =
			getServerTimeResponseBean.getGetServerTimeResponse();
		System.out.println("Status : " + getServerTimeResponse.getStatus());
		System.out.println("Status message : " + getServerTimeResponse.getStatusMessage());
		System.out.println("Server time is : " + getServerTimeResponse.getTimestamp().getTime());
	}
}