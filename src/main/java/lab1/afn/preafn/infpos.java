package lab1.afn.preafn;


    import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class infpos {

   /*  
	public static final Map<Character, Integer> precedenceMap;
	static {
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		map.put('(', 1);
		map.put('|', 2);
		map.put('.', 3); // explicit concatenation operator
		map.put('?', 4);
		map.put('*', 4);
		map.put('+', 4);
		map.put('^', 5);
		precedenceMap = Collections.unmodifiableMap(map);
	};


	private static Integer getPrecedence(Character c) {
		Integer precedence = precedenceMap.get(c);
		return precedence == null ? 6 : precedence;
	}

	
	private static String formatRegEx(String regex) {
		String res = new String();
		List<Character> allOperators = Arrays.asList('|', '?', '+', '*', '^');
		List<Character> binaryOperators = Arrays.asList('^', '|');

		for (int i = 0; i < regex.length(); i++) {
			Character c1 = regex.charAt(i);

			if (i + 1 < regex.length()) {
				Character c2 = regex.charAt(i + 1);

				res += c1;

				if (!c1.equals('(') && !c2.equals(')') && !allOperators.contains(c2) && !binaryOperators.contains(c1)) {
					res += '.';
				}
			}
		}
		res += regex.charAt(regex.length() - 1);

		return res;
	}

	
	public static String infixToPostfix(String regex) {
		String postfix = new String();

		Stack<Character> stack = new Stack<Character>();

		String formattedRegEx = formatRegEx(regex);

		for (Character c : formattedRegEx.toCharArray()) {
			switch (c) {
				case '(':
					stack.push(c);
					break;

				case ')':
					while (!stack.peek().equals('(')) {
						postfix += stack.pop();
					}
					stack.pop();
					break;

				default:
					while (stack.size() > 0) {
						Character peekedChar = stack.peek();

						Integer peekedCharPrecedence = getPrecedence(peekedChar);
						Integer currentCharPrecedence = getPrecedence(c);

						if (peekedCharPrecedence >= currentCharPrecedence) {
							postfix += stack.pop();
						} else {
							break;
						}
					}
					stack.push(c);
					break;
			}

		}

		while (stack.size() > 0)
			postfix += stack.pop();

		return postfix;
	}*/
	public static String infixToPostfix(String regex) {
		StringBuilder postfix = new StringBuilder();
		Stack<Character> operatorStack = new Stack<>();
		Map<Character, Integer> precedence = new HashMap<>();
		precedence.put('(', 1);
		precedence.put('|', 2);
		precedence.put('.', 3); // explicit concatenation operator
		precedence.put('?', 4);
		precedence.put('*', 4);
		precedence.put('+', 4);
		precedence.put('^', 5);
	
		for (int i = 0; i < regex.length(); i++) {
			char c = regex.charAt(i);
	
			if (c == '(') {
				operatorStack.push(c);
			} else if (c == ')') {
				while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
					postfix.append(operatorStack.pop());
				}
				if (!operatorStack.isEmpty() && operatorStack.peek() == '(') {
					operatorStack.pop();
				}
			} else if (isOperator(c)) {
				while (!operatorStack.isEmpty() && precedence.get(c) <= precedence.get(operatorStack.peek())) {
					postfix.append(operatorStack.pop());
				}
				operatorStack.push(c);
			} else {
				postfix.append(c);
			}
		}
	
		while (!operatorStack.isEmpty()) {
			postfix.append(operatorStack.pop());
		}
	
		return postfix.toString();
	}
	
	private static boolean isOperator(char c) {
		return c == '|' || c == '.' || c == '?' || c == '*' || c == '+' || c == '^';
	}
    public static void main(String[] args)
    {
       String regex= "(a|b)*a.b.b";
       String res= infixToPostfix(extensionReg.transform_Regex(regex) );
       System.out.println(res);
    }
}

