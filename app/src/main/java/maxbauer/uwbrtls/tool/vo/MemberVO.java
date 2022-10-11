package maxbauer.uwbrtls.tool.vo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class MemberVO implements Parcelable { //implements Parcelable을 설정한 이유는 각각의 Activity로 파라미터를 넘겨줄때 사용하기 위해서다. MemberVO를 생성할때 기본 아이디, 비밀번호를 담아 초기화 할 수 있도록 해주었다.
    @SerializedName("mem_no")
    private int mem_no;
    @SerializedName("mem_id")
    private String mem_id;
    @SerializedName("mem_pw")
    private String mem_pw;
    @SerializedName("mem_name")
    private String mem_name;

    public MemberVO(String mem_id, String mem_pw){
        this.mem_id = mem_id;
        this.mem_pw = mem_pw;
    }

    public int getMem_no(){
        return mem_no;
    }
    public void setMem_no(int mem_no){
        this.mem_no = mem_no;
    }
    public String getMem_id(){
        return mem_id;
    }
    public void setMem_id(String mem_id){
        this.mem_id = mem_id;
    }
    public String getMem_pw(){
        return mem_pw;
    }
    public void setMem_pw(String mem_pw){
        this.mem_pw = mem_pw;
    }
    public String getMem_name(){
        return mem_name;
    }
    public void setMem_name(String mem_name){
        this.mem_name = mem_name;
    }

    protected MemberVO(Parcel in) {
        mem_no = in.readInt();
        mem_id = in.readString();
        mem_pw = in.readString();
        mem_name = in.readString();
    }

    public static final Creator<MemberVO> CREATOR = new Creator<MemberVO>() {
        @Override
        public MemberVO createFromParcel(Parcel in) {
            return new MemberVO(in);
        }

        @Override
        public MemberVO[] newArray(int size) {
            return new MemberVO[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mem_no);
        parcel.writeString(mem_id);
        parcel.writeString(mem_pw);
        parcel.writeString(mem_name);
    }
}