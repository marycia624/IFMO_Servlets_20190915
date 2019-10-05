import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.*;
import java.util.*;


@WebServlet(
        name = "Calculator",
        urlPatterns = "/calc"
)

public class Calculator extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        String equation = req.getParameter("equation");
        Map<String, String[]> prmts = req.getParameterMap();
        equation = equation.replaceAll("\\s+", "");

        while (ConsistOfLetters(equation)) {
            for (int i = 0; i < equation.length(); i++) {
                if (equation.charAt(i) >= 'a' && equation.charAt(i) <= 'z') {
                    equation = equation.replace(String.valueOf(equation.charAt(i)), String.valueOf(prmts.get(String.valueOf(equation.charAt(i)))[0]));
                }
            }
        }

        Parser n = new Parser();
        List<String> expression = n.parse(equation);

        out.println(calc(expression));


    }

    public static Integer calc(List<String> postfix) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (String x : postfix) {
            if (x.equals("+")) stack.push(stack.pop() + stack.pop());
            else if (x.equals("-")) {
                Integer b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            } else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
            else if (x.equals("/")) {
                int b = stack.pop(), a = stack.pop();
                stack.push(a / b);
            } else stack.push(Integer.valueOf(x));
        }
        return stack.pop();
    }

    public boolean ConsistOfLetters(String tmp) {
        for (int i = 0; i < tmp.length(); i++) {
            if ('a' < tmp.charAt(i) && 'z' > tmp.charAt(i)) return true;
        }
        return false;
    }
}