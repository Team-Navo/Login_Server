import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public  class ParsingToJSON {
    JSONParser jParser;
    JSONObject jObject;
    Object obj;
    DBConnection dbConn;

    public ParsingToJSON(String recvData) throws ParseException {
        jParser = new JSONParser();
        obj = jParser.parse(recvData);
        jObject = (JSONObject) obj;

        System.out.println("[오브젝트의 개수 : " + jObject.size() + "]");

        // 클라이언트로 부터 받은 데이터의 개수에 따른 다른 처리
        int sizeCheck = jObject.size();
        switch(sizeCheck) {
            case 2: // 로그인
                login((String)jObject.get("id"), (String)jObject.get("pw"));
                break;
            case 6: // 회원가입
                join((String)jObject.get("name"), (int)jObject.get("birth"), (int)jObject.get("phone"), (String)jObject.get("id"), (String)jObject.get("pw"), (String)jObject.get("qna"));
                break;
            case 3: // ID 찾기
                findId((String)jObject.get("name"), (int)jObject.get("birth"), (int)jObject.get("phone"));
                break;
            case 5: // PW 찾기
                findPW((String)jObject.get("id"), (String)jObject.get("name"), (int)jObject.get("birth"), (int)jObject.get("phone"), (String)jObject.get("qna"));
                break;
            default:
                // 클라이언트한테 잘못 보냈다고 알리기
                break;
        }
    }

    // 로그인
    public void login(String id, String pw) {
        try {
            User user = new User();
            user.setId(id);
            user.setPw(pw);

            dbConn = new DBConnection();
            dbConn.selectLogin(user);

        } catch (Exception e) {
            System.err.println("[로그인 실패]");
            e.printStackTrace();
        }
    }
    
    // 회원가입
    public boolean join(String name, int birth, int phone, String id, String pw, String qna) { return true; }
    
    // ID 찾기
    public boolean findId(String name, int birth, int phone) { return true; }
    
    // PW 찾기
    public boolean findPW(String id, String name, int birth, int phone, String qna) { return true; }
}
