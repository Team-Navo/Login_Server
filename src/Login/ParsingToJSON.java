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

        System.out.println("[오브젝트 개수 : " + jObject.size() + "]");

        // 클라이언트로 부터 받은 데이터의 개수에 따른 다른 처리
        int sizeCheck = jObject.size();
        switch(sizeCheck) {
            case 1: // pw 변경
                changePW((String)jObject.get("pw"));
                break;
            case 2: // 로그인
                login((String)jObject.get("id"), (String)jObject.get("pw"));
                break;
            case 6: // 회원가입
                join((String)jObject.get("name"), (String)jObject.get("birth"), (String)jObject.get("phone"), (String)jObject.get("id"), (String)jObject.get("pw"), (String)jObject.get("qna"));
                break;
            case 3: // ID 찾기
                findId((String)jObject.get("name"), (String)jObject.get("birth"), (String)jObject.get("phone"));
                break;
            case 5: // PW 찾기
                findPW((String)jObject.get("id"), (String)jObject.get("name"), (String)jObject.get("birth"), (String)jObject.get("phone"), (String)jObject.get("qna"));
                break;
            default:
                ServerRecv_Send.checkData(false);
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
            dbConn.userLogin(user);

        } catch (Exception e) {
            System.err.println("[로그인 실패]");
            e.printStackTrace();
        }
    }
    
    // 회원가입
    public void join(String name, String birth, String phone, String id, String pw, String qna) {
        try {
            User user = new User();
            user.setName(name);
            user.setBirth(birth);
            user.setPhone(phone);
            user.setId(id);
            user.setPw(pw);
            user.setQna(qna);

            dbConn = new DBConnection();
            dbConn.createUser(user);

        } catch (Exception e) {
            System.err.println("[회원가입 실패]");
            e.printStackTrace();
        }
    }
    
    // ID 찾기
    public void findId(String name, String birth, String phone) { }
    
    // PW 찾기
    public void findPW(String id, String name, String birth, String phone, String qna) { }

    // PW 변경
    public void changePW(String pw) { }
}
