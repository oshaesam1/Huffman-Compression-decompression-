public class Dictionary {
    String code;
    char symbol;
    public Dictionary()
    {

    }
    public Dictionary(String code ,char symbol){
        this.code = code;
        this.symbol=symbol;
    }
    public void display ()
    {
        System.out.print("<<"+symbol+","+code+">>");

    }
}
