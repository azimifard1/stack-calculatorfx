package calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.Stack;

public class MainController{
    // دکمه ها
    @FXML
    Button one;
    @FXML
    Button two;
    @FXML
    Button three;
    @FXML
    Button four;
    @FXML
    Button five;
    @FXML
    Button six;
    @FXML
    Button seven;
    @FXML
    Button eight;
    @FXML
    Button nine;
    @FXML
    Label numbersShow;
    @FXML
    Label error;
    @FXML
    Button zero;
    @FXML
    Button equal;
    @FXML
    Button plus;
    @FXML
    Button minus;
    @FXML
    Button divide;
    @FXML
    Button multiply;
    @FXML
    TextField output;
    @FXML
    Button clear;

    String input= "";//رشته ای که عبارت داخلش ذخیره میشه

    //بصورت پیشفرض گفتیم که صفحه نمایش اعداد رو نشه بصورت دستی چیزی درش وارد کرد
    public void initialize(){
        output.setEditable(false);
    }

    //این متد وصله به دکمه ها
    @FXML
    public void Actions(ActionEvent e){
        //اگه روی دکمه 1 کلیک شد
        //متد setInput اجرایی بشه
        if(e.getSource() == one){
            setInput("1");
        }
        if(e.getSource() == two){
            setInput("2");
        }
        if(e.getSource() == three){
            setInput("3");
        }
        if(e.getSource() == four){
            setInput("4");
        }
        if(e.getSource() == five){
           setInput("5");
        }
        if(e.getSource() == six){
           setInput("6");
        }
        if(e.getSource() == seven){
           setInput("7");
        }
        if(e.getSource() == eight){
            setInput("8");
        }
        if(e.getSource() == nine){
            setInput("9");
        }
        if(e.getSource() == zero){
           setInput("0");
        }
        //اگه روی پلاس کلیک شد
        if(e.getSource()==plus) {
            //اگه اخرین کاراکتر اضافه شده به عبارت، یک عملگر نبود، اونوقت عملگر پلاس رو اضافه کن به عبارت
            if(!lastInputAsOperator()) {
                input +="+";
                output.setText("");

            }

        }
        if(e.getSource()==minus){
            if(!lastInputAsOperator()) {
                input += "-";
                output.setText("");
            }
        }
        if(e.getSource()==divide){
            if(!lastInputAsOperator()) {
                input +="/";
                output.setText("");
            }
        }
        if(e.getSource()==multiply){
            if(!lastInputAsOperator()) {
                input +="*";
                output.setText("");
                System.out.println(input);
            }
        }
        //اگه روی مساوی کلیک شد، عبارت رو محاسبه کن و هم بجای اینپوت بزار و هم توی خروجی نمایش بده
        if(e.getSource()==equal){
           System.out.println(input);
           input = Evaluate(input)+"";
           output.setText(input);
        }

        //اگه روی دکمه کلیر کلیک شد، هم صفحه و هم عبارت خالی میشه
        if(e.getSource()==clear){
            numbersShow.setText("");
            input = "";
            output.clear();
        }
    }

    //متد ست اینپوت که چک میکنه اگه عبارتمون صفر خالی بود و روی یکی از اعداد کلیک شد، اون عدد رو جایگزین صفر کنه
    //در غیر اینصورت عدد رو به عبارت قبلی اضافه و روی صفحه نمایش بده
    public void setInput(String number){
        if(input.equals("0")){
            input = number;
        }else {
            input += number;
            output.setText(output.getText() + number);
        }
        System.out.println(input);
    }
    //چک میکنه که آیا اخرین کاراکتر عبارت، یه عملگره یا نه
    public boolean lastInputAsOperator(){
        if(input.length()==0){
            return false;
        }
        char lastChar = input.charAt(input.length()-1);
        return lastChar=='+' || lastChar=='-' || lastChar=='*'||lastChar=='/';
    }
    //متد ارزیابی عبارت ریاضی و محاسبه بر اساس پشته
    public static double Evaluate(String input)
    {

        //استک برای اوپراتور ها
        Stack<Integer> op  = new Stack<Integer>();
        //استک برای عملگر ها
        Stack<Double> val = new Stack<Double>();
        //استک های موقتی برای اپراتور ها و عملگر ها
        Stack<Integer> optmp  = new Stack<Integer>();
        Stack<Double> valtmp = new Stack<Double>();


        input = "0" + input;

        input = input.replaceAll("-","+-");

        String temp = "";
        //ارزیابی عبارت و ذخیره عملگر و اوپراتور در پشته ها متناسب با نوع. اگه عملگر باشه میره تو opt و اگه عملوند باشه میره تو val
        for (int i = 0;i < input.length();i++)
        {

            char ch = input.charAt(i);
            if (ch == '-')
                temp = "-" + temp;
            else if (ch != '+' &&  ch != '*' && ch != '/')
                temp = temp + ch;
            else {
                val.push(Double.parseDouble(temp));
                op.push((int)ch);
                temp = "";
            }
        }

        val.push(Double.parseDouble(temp));
        //ذخیره عملگر ها بترتیب اولویتشون
        char operators[] = {'/','*','+'};


        //ارزیابی
        for (int i = 0; i < 3; i++) {
            boolean it = false;
            while (!op.isEmpty())
            {
                int optr = op.pop();
                double v1 = val.pop();
                double v2 = val.pop();

                //بر اساس اولویت عملگر ها، دو عدد داخل عبارت رو محاسبه و داخل استک موقتی پوش میکنیم
                if (optr == operators[i]) {
                    if (i == 0) {
                        valtmp.push(v2 / v1);
                        it = true;
                        break;
                    }
                    else if (i == 1)
                    {
                        valtmp.push(v2 * v1);
                        it = true;
                        break;
                    }
                    else if (i == 2)
                    {
                        valtmp.push(v2 + v1);
                        it = true;
                        break;
                    }
                } else {
                    valtmp.push(v1);
                    val.push(v2);
                    optmp.push(optr);

                }
            }
            //پوش کردن آیتم های داخل استک هایم موقتی به داخل استک های اصلی
            while (!valtmp.isEmpty())
                val.push(valtmp.pop());

            while (!optmp.isEmpty())
                op.push(optmp.pop());

            if (it)
                i--;

        }
        //اخرین مقدار موجود در استک مقادیر، همون جواب نهایی هستش که ریترن میکنیم
        return val.pop();

    }

}

