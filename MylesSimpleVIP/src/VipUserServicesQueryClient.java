import java.rmi.RemoteException;
import com.symantec.vipuserservices.wsclient.QueryServiceStub;
import com.symantec.vipuserservices.wsclient.QueryServiceStub.GetServerTimeRequest;
import com.symantec.vipuserservices.wsclient.QueryServiceStub.GetServerTimeRequestType;
import com.symantec.vipuserservices.wsclient.QueryServiceStub.GetServerTimeResponse;
import com.symantec.vipuserservices.wsclient.QueryServiceStub.GetServerTimeResponseType;
import com.symantec.vipuserservices.wsclient.QueryServiceStub.RequestIdType;

public class VipUserServicesQueryClient {
public static void main(String[] args) throws RemoteException {
String pathToP12File = "C:/workspace/certs/clientcert.p12";
String password = "password";
System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
System.setProperty("javax.net.ssl.keyStore", pathToP12File);
System.setProperty("javax.net.ssl.keyStorePassword", password);
QueryServiceStub queryServiceStub = new QueryServiceStub("https://userservices-auth.vip.symantec.com/vipuserservices/QueryService_1_2");
GetServerTimeRequest getServerTimeRequestBean = new GetServerTimeRequest();
GetServerTimeRequestType getServerTimeRequest = new GetServerTimeRequestType();
getServerTimeRequestBean.setGetServerTimeRequest(getServerTimeRequest);

RequestIdType requestIdType = new RequestIdType();
requestIdType.setRequestIdType("rqstId" + System.currentTimeMillis());
getServerTimeRequest.setRequestId(requestIdType);
GetServerTimeResponse getServerTimeResponseBean = queryServiceStub.getServerTime(getServerTimeRequestBean);
GetServerTimeResponseType getServerTimeResponse = getServerTimeResponseBean.getGetServerTimeResponse();
System.out.println("Status : " + getServerTimeResponse.getStatus());
System.out.println("Status message : " + getServerTimeResponse.getStatusMessage());
System.out.println("Server time is : " + getServerTimeResponse.getTimestamp().getTime());
}
}