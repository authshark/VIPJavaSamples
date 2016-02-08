import java.rmi.RemoteException;
import com.symantec.vipuserservices.wsclient.ManagementServiceStub;
import com.symantec.vipuserservices.wsclient.ManagementServiceStub.*;

public class VipUserServicesMgmtClient {
public static void main(String[] args) throws RemoteException {
	
	System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
	System.setProperty("javax.net.ssl.keyStore", "c:/workspace/certs/clientcert.p12");
	System.setProperty("javax.net.ssl.keyStorePassword", "Apple1995!");
	
	ManagementServiceStub managementServiceStub = new ManagementServiceStub("https://userservices-auth.vip.symantec.com/vipuserservices/ManagementService_1_1");

	DeleteUserRequest deleteUserRequestBean = new DeleteUserRequest();
	DeleteUserRequestType deleteUserRequest = new DeleteUserRequestType();
	deleteUserRequestBean.setDeleteUserRequest(deleteUserRequest);

	RequestIdType requestIdType = new RequestIdType();
	requestIdType.setRequestIdType("rqstId" + System.currentTimeMillis());
	
	UserIdType userIdType = new UserIdType();
	userIdType.setUserIdType("oct1");

	deleteUserRequest.setRequestId(requestIdType);
	deleteUserRequest.setUserId(userIdType);	

	DeleteUserResponse deleteUserResponseBean = managementServiceStub.deleteUser(deleteUserRequestBean);
	DeleteUserResponseType deleteUserResponse = deleteUserResponseBean.getDeleteUserResponse();

	System.out.println("Status : " + deleteUserResponse.getStatus());
	System.out.println("Status message : " + deleteUserResponse.getStatusMessage());
}	
}