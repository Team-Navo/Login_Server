public class User {
    private String name, id, pw, qna;
    private int birth, phone, serial;

    public User () {}

    public void setName(String name) { this.name = name; }
    public void setBirth(int birth) { this.birth = birth; }
    public void setPhone(int phone) { this.phone = phone; }
    public void setId(String id) { this.id = id; }
    public void setPw(String pw) { this.pw = pw; }
    public void setQna(String qna) { this.qna = qna; }

    public String getName() { return name; }
    public int getBirth() { return birth; }
    public int getPhone() { return phone; }
    public String getId() { return id; }
    public String getPw() { return pw; }
    public String getQna() { return qna; }

    @Override
    public String toString() { return "User [name=" + name + "]"; }
}
