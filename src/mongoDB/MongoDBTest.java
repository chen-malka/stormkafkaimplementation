package mongoDB;

import backtype.storm.generated.GlobalStreamId;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.MessageId;
import backtype.storm.tuple.Tuple;
import com.kafkastorm.example.mongoDB.ConnectProvider;
import com.kafkastorm.example.subscriber.ActiveUserSaverBolt;
import com.mongodb.*;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

public class MongoDBTest {

    static
    DB db= ConnectProvider.getConnect();

    private static final String DATABASE_NAME = "embedded";

    private MongodExecutable mongodExe;
    private MongodProcess mongod;
    private Mongo mongo;

//    @Before
//    public void beforeEach() throws Exception {
//        MongoDBRuntime runtime = MongoDBRuntime.getDefaultInstance();
//        mongodExe = runtime.prepare(new MongodConfig(Version.V2_3_0, 12345, Network.localhostIsIPv6()));
//        mongod = mongodExe.start();
//        mongo = new Mongo("localhost", 12345);
//    }
//
//    @After
//    public void afterEach() throws Exception {
//        if (this.mongod != null) {
//            this.mongod.stop();
//            this.mongodExe.stop();
//        }
//    }



    @Test
    public void connectionOnMemoryTest() {
        DBCollection table = db.getCollection("user");
        BasicDBObject document = new BasicDBObject();
        document.put("name", "Ortal");
        document.put("count", 1);
        table.insert(document);
    }

    @Test
    public void selectTest() {
        DBCollection users = db.getCollection("user");
        Iterator<DBObject> i = users.find().iterator();
        while (i.hasNext()) {
            DBObject nextElement =  i.next();
            System.out.println("user name : " +  nextElement.get("name") + " ; user count " + nextElement.get("count"));
        }
    }

    ActiveUserSaverBolt a = new ActiveUserSaverBolt();


    @Test
    public void updateTest() {
        a.execute(new Tuple() {
            @Override
            public int size() {
                return 1;
            }

            @Override
            public int fieldIndex(String s) {
                return 1;
            }

            @Override
            public boolean contains(String s) {
                return false;
            }

            @Override
            public Object getValue(int i) {
                return null;
            }

            @Override
            public String getString(int i) {
                return null;
            }

            @Override
            public Integer getInteger(int i) {
                return null;
            }

            @Override
            public Long getLong(int i) {
                return null;
            }

            @Override
            public Boolean getBoolean(int i) {
                return null;
            }

            @Override
            public Short getShort(int i) {
                return null;
            }

            @Override
            public Byte getByte(int i) {
                return null;
            }

            @Override
            public Double getDouble(int i) {
                return null;
            }

            @Override
            public Float getFloat(int i) {
                return null;
            }

            @Override
            public byte[] getBinary(int i) {
                return new byte[0];
            }

            @Override
            public Object getValueByField(String s) {
                return "anton";
            }

            @Override
            public String getStringByField(String s) {
                return null;
            }

            @Override
            public Integer getIntegerByField(String s) {
                return null;
            }

            @Override
            public Long getLongByField(String s) {
                return null;
            }

            @Override
            public Boolean getBooleanByField(String s) {
                return null;
            }

            @Override
            public Short getShortByField(String s) {
                return null;
            }

            @Override
            public Byte getByteByField(String s) {
                return null;
            }

            @Override
            public Double getDoubleByField(String s) {
                return null;
            }

            @Override
            public Float getFloatByField(String s) {
                return null;
            }

            @Override
            public byte[] getBinaryByField(String s) {
                return new byte[0];
            }

            @Override
            public List<Object> getValues() {
                return null;
            }

            @Override
            public Fields getFields() {
                return null;
            }

            @Override
            public List<Object> select(Fields fields) {
                return null;
            }

            @Override
            public GlobalStreamId getSourceGlobalStreamid() {
                return null;
            }

            @Override
            public String getSourceComponent() {
                return null;
            }

            @Override
            public int getSourceTask() {
                return 0;
            }

            @Override
            public String getSourceStreamId() {
                return null;
            }

            @Override
            public MessageId getMessageId() {
                return null;
            }
        });
    }


}
