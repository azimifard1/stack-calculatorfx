package calc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.Stack;

public class Main extends Application {

    public static void main(String[] args) {
	    launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static double Evaluate(String input)
    {

        //استک برای اوپراتور ها
        Stack<Integer> op  = new Stack<Integer>();
        //استک برای عملگر ها
        Stack<Double> val = new Stack<Double>();
        //استک های موقتی برای اپراتور ها و عملگر ها
        Stack<Integer> optmp  = new Stack<Integer>();
        Stack<Double> valtmp = new Stack<Double>();

        //به اول ورودی یدونه صفر اضافه میکنیم که احیانا اولین عدد منفی بود، اون منفی بعنوان علامت در نظرگرفته بشه
        input = "0" + input;
        //منفی ها رو با -+ جایگزین میکنیم
        // این در واقع مثل این میمونه که عدد قبلی رو با عدد منفی بعدی جمع زدیم
        input = input.replaceAll("-","+-");

        String temp = "";
        //ارزیابی عبارت و ذخیره عملگر و اوپراتور در پشته ها متناسب با نوع. اگه عملگر باشه میره تو opt و اگه عملوند باشه میره تو val
        for (int i = 0;i < input.length();i++)
        {

            char ch = input.charAt(i);
            if (ch == '-')
                temp = "-" + temp;
            else if (ch != '+' &&  ch != '*' && ch != '/')
                //اگه کاراکترمون چیزی غیر از عملگر بود، اون عدد رو به تمپ اضافه میکنیم
                temp = temp + ch;
            else {
                //اگه اون کاراکتر عملگر بود، اول تمپ رو که عدد قبل از اون عملگر بوده رو داخل استک مقادیر پوش میکنیم
                val.push(Double.parseDouble(temp));
                //و همینطور خود عملگر رو هم داخل استک عملگر ها پوش میکنیم
                op.push((int)ch);
                //تمپ رو خالی میکنیم که بریم سراغ ارزیابی عبارت بعدی
                temp = "";
            }
        }
        //در نهایت یه عدد هم میمونه بعنوان تمپ که اونم باید داخل استک مقادیر پوش کنیم
        val.push(Double.parseDouble(temp));
        //ذخیره عملگر ها بترتیب اولویتشون
        char operators[] = {'/','*','+'};


        //ارزیابی
        for (int i = 0; i < 3; i++) {
            boolean it = false;

            //تازمانی که استک عملگر هامون خالی نشده
            while (!op.isEmpty())
            {
                //میایم عملگر رو پاپ میکنیم
                // دو عدد سر استک هم پاپ میکنیم
                int optr = op.pop();
                double v1 = val.pop();
                double v2 = val.pop();

                //حالا با توجه به عملگری که پاپ کردیم، بین دو تا عدد عملیات ریاضی انجام میدیم
                // مثلا اگه عملگر تقسیم بود پس هر دو عدد رو برهم تقسیم میکنیم
                //نتیجه رو داخل استک موقتی مقادیر پوش میکنیم
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
