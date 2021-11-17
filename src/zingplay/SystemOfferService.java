package zingplay;

import com.google.gson.Gson;
import zingplay.data.*;
import zingplay.offer.OfferListener;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class SystemOfferService {
    private static SystemOfferService instance;
    public static SystemOfferService getInstance(){
        if(instance == null){
            SystemOfferService.instance = new SystemOfferService();
        }
        return instance;
    }

    private SystemOfferController controller;
    private SystemOfferService(){
        controller = new SystemOfferController();
    }
    SystemOfferController getController(){
        return controller;
    }

    //1. khởi tạo config
    public void start(){
        start("");
    }
    public void start(String pathConfig){
        controller.init(pathConfig);
    }
    //2. gắn listener
    public void setListener(SystemOfferListener listener){
        controller.setListener(listener);
    }

    public void send(Sender sender){
        controller.send(sender);
    }
    public void sendWith(RequestOfferDetail sender, OfferListener listener){
        controller.sendWith(sender,listener);
    }

    public static void main(String[] args) throws InterruptedException {
//        String a ="{\"u\":\"1000\",\"o\":[{\"id\":\"60d164ab6153960fb8934002\",\"n\":\"sonn\",\"p\":1,\"$\":{\"c\":\"all\",\"o\":300,\"p\":20.0,\"b\":0},\"i\":0,\"t\":0,\"s\":1624208400000,\"e\":1624381200000,\"l\":[{\"id\":\"4\",\"value\":3},{\"id\":\"0\",\"value\":5000000}]}]}";
//        UserOffers userOffers = new Gson().fromJson(a, UserOffers.class);
        SystemOfferService.getInstance().setListener(new SystemOfferListener() {
            @Override
            public void onReceiveOffer(UserOffers offer) {
                System.out.println("=>>>>>>> onReceiveOffer : " + offer);
            }

            @Override
            public void onReceiveDataCustom(UserDataCustom userDataCustom) {
                System.out.println("=>>>>>>> onReceiveDataCustom : " + userDataCustom);
                long now = System.currentTimeMillis();
                for (DataCustom dataCustom : userDataCustom.getDataCustoms()){
                    SystemOfferService.getInstance().send(new TrackingDataCustom(
                            Integer.parseInt(userDataCustom.getUserId()), dataCustom.getTypeCustom(), dataCustom.getDataId(), now, "dev"));
                }

            }

            @Override
            public void onConnected(String msg) {
                System.out.println("=>>>>>>> On connected");
            }
        });
        SystemOfferService.getInstance().start();
        Thread.sleep(5000);

        SystemOfferService.getInstance().sendTrackingData(1000,1010);
        SystemOfferService.getInstance().sendRequest(1000, 1010);

//        SystemOfferService.getInstance().sendWith(new RequestOfferDetail("123", ""), new OfferListener() {
//            @Override
//            public void onReceived(Offer offer) {
//                System.out.println(offer);
//            }
//
//            @Override
//            public void onFail(SOError error) {
//                System.out.printf("on fail" + error.name());
//            }
//        });

        // SystemOfferService.getInstance().sendTestOffer(new UserOffers());
        // SystemOfferService.getInstance().send(new RequestOffer());
        // SystemOfferService.getInstance().send(new TrackingLogin());
        // SystemOfferService.getInstance().send(new TrackingBuyOffer());
        // SystemOfferService.getInstance().send(new TrackingPayment());
        // SystemOfferService.getInstance().send(new TrackingStateGame());
/*

//1. găn listener để nhận được offer khi send request
SystemOfferService.getInstance().setListener(new SystemOfferListener() {
    @Override
    public void onReceiveOffer(UserOffers offer) {
        //handle cmd request send back client => show offer
        String userId = offer.getUserId();
    }

    @Override
    public void onConnected(String msg) {
        //phản hồi từ tool khi call SystemOfferService.getInstance().sendTestConnect(); để test connect đến tool
        //System.out.println("on connected");
    }
});
//2. start kết nối
String pathConfig = "";//đường dẫn trỏ đến file "system.offer.properties", default = '/res/system.offer.properties'
SystemOfferService.getInstance().start(pathConfig);

//3. thực hiện send các chức năng mong muốn qua tool
// phần tracking -> gọi tương ứng các lúc xảy ra sự kiện -> không nhận phản hồi
int userId = 0;//chính là userId trong game socket
long timeCreate = System.currentTimeMillis();//second|millis đều được, thời gian account được tạo profile.getTimeCreate();
long timeLastOnline = System.currentTimeMillis();//second|millis đều được, thời gian cuối cùng user online, cũng chính là thời gian lúc gửi
int channelIdx = 1;//channels hiện tại user có thể join -> 0,1,2,3,4 (kênh user chơi với số tiền hiện tại user đang có)
long totalGame = 0;//tổng số ván chơi của user
long timeCurrent = System.currentTimeMillis();//second|millis đều được, thời gian hiện tại của server
String idOffer = "60b8a257d745a162ab5bf8dc";//mã id bên tool gửi về trong UserOffers->Offer->id (cái id này)
String country = "ph";//country của user (2 kí tự, viết thường không in hoa) ví dụ ph,mm,id,v.v...
float price = 0;//giá mua user mua UserOffers->List<Offer> offers-> Price price|HashMap<String,Price> customPrice -> price, cũng chính là giá bán offer
long costPack = 1;//mệnh giá khi user mua có thể float,int
String channelPayment = "iap";//kênh thanh toan mua user mua -> được setup đồng bộ với key bên tool phần channelPayment

//3.1 test thử kết nối với tool có thông nhau không
SystemOfferService.getInstance().sendTestConnect();
//3.2 mong muốn nhận về đúng với offer vừa gửi lên (mục đích cho việc test khi chưa có data
// -> sẽ nhận phản hồi ở listener.onReceiverOffer(UserOffer)
SystemOfferService.getInstance().sendTestOffer(new UserOffers());
//3.3 khi muốn lấy offer từ tool thì gọi
// -> sẽ nhận phản hồi ở listener.onReceiverOffer(UserOffer) client -> get offer -> send request offer
SystemOfferService.getInstance().send(new RequestOffer(userId,timeCurrent,country)); //country = "" <- get giá default
//3.4 khi login -> call
SystemOfferService.getInstance().send(new TrackingLogin(userId, timeCreate, timeLastOnline, channelIdx, totalGame, timeCurrent));
//3.5 khi user buy offer -> call
SystemOfferService.getInstance().send(new TrackingBuyOffer(userId, idOffer, country, price, timeCurrent));
//3.6 khi user payment -> trường hợp user mua offer (call cả 2 cái TrackingBuyOffer() & TrackingPayment()) vì 2 cái xử lý khác nhau)
SystemOfferService.getInstance().send(new TrackingPayment(userId, costPack, channelPayment, country, timeCurrent));
//3.7 khi cập nhật trạng thái game
SystemOfferService.getInstance().send(new TrackingStateGame(userId, channelIdx, totalGame, timeCurrent));
*/

    }

    public void sendRequest(int uIdFrom, int uIdTo){
        new Thread(() -> {
            int uId = uIdFrom;
            while (true) {
                if (uId >= uIdTo){
                    return;
                }
                long time = new Date().getTime();
                SystemOfferService.getInstance().send(new RequestOffer(uId, time, "dev"));
                SystemOfferService.getInstance().send(new RequestDataCustom(uId, time, "dev"));
                uId++;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendTrackingData(int uIdFrom, int uIdTo){
        new Thread(() -> {
            int uId = uIdFrom;
            while (true) {
                if (uId >= uIdTo){
                    return;
                }
                long time = new Date().getTime();
                Tracking tracking = new Tracking(uId, time, time);
                tracking.setTracking(TrackingCommon.ChannelIdx, (uId % 3) == 0 ? 1 : 2);
                tracking.setTracking(TrackingCommon.TimeOnline, time);
                tracking.setTracking(TrackingCommon.channelPayments, uId%3 == 0 ? "iap" : "creditcard");
                tracking.setTracking(TrackingCommon.lastPaidAmounts, (uId%4 == 0 ? "50" : "100"));
                tracking.setTracking(TrackingCommon.timesPaid, (uId % 5) + 1);
                tracking.setTracking(TrackingCommon.TotalGame, ((uId % 5) + 1 ) * 5);

                ValueCondition valueCondition = new ValueCondition();
                valueCondition.setParamTracking(TrackingCommon.currency, "USD");
                valueCondition.setParamTracking(TrackingCommon.cost, 10);
                tracking.setTracking(TrackingCommon.lastPaidPack, valueCondition);

                SystemOfferService.getInstance().send(tracking);
                uId++;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendTestConnect() {
        controller.sendTest("connect");
    }
    public void sendTestOffer(UserOffers offers) {
        Gson gson = new Gson();
        String s = gson.toJson(offers);
        controller.sendTest(s);
    }
}
