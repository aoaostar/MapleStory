package tools;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;

public class Eval
{
    private final Operation rootOperation;
    
    public static BigDecimal eval(final String expression, final Map<String, BigDecimal> variables) {
        return new Eval(expression).eval(variables);
    }
    
    public static BigDecimal eval(final String expression) {
        return new Eval(expression).eval();
    }
    
    public Eval(final String expression) {
        this.rootOperation = new Compiler(expression).compile();
    }
    
    public BigDecimal eval(final Map<String, BigDecimal> variables) {
        return this.rootOperation.eval(variables);
    }
    
    public BigDecimal eval() {
        return this.eval((Map<String, BigDecimal>)null);
    }
    
    @Override
    public String toString() {
        return this.rootOperation.toString();
    }
    
    public enum Type
    {
        ARITHMETIC("arithmetic"), 
        BOOLEAN("boolean");
        
        String name;
        
        private Type(final String name) {
            this.name = name;
        }
    }
    
    public static class Tokeniser
    {
        static Character START_NEW_EXPRESSION;
        private final String string;
        private int position;
        private Operator pushedBackOperator;
        
        Tokeniser(final String string) {
            this.pushedBackOperator = null;
            this.string = string;
            this.position = 0;
        }
        
        int getPosition() {
            return this.position;
        }
        
        void setPosition(final int position) {
            this.position = position;
        }
        
        void pushBack(final Operator operator) {
            this.pushedBackOperator = operator;
        }
        
        Operator getOperator(final char endOfExpressionChar) {
            if (this.pushedBackOperator != null) {
                final Operator operator = this.pushedBackOperator;
                this.pushedBackOperator = null;
                return operator;
            }
            final int len = this.string.length();
            char ch = '\0';
            while (this.position < len && Character.isWhitespace(ch = this.string.charAt(this.position))) {
                ++this.position;
            }
            if (this.position == len) {
                if (endOfExpressionChar == '\0') {
                    return Operator.END;
                }
                throw new RuntimeException("missing " + endOfExpressionChar);
            }
            else {
                ++this.position;
                if (ch == endOfExpressionChar) {
                    return Operator.END;
                }
                switch (ch) {
                    case '+': {
                        return Operator.ADD;
                    }
                    case '-': {
                        return Operator.SUB;
                    }
                    case '/': {
                        return Operator.DIV;
                    }
                    case '%': {
                        return Operator.REMAINDER;
                    }
                    case '*': {
                        return Operator.MUL;
                    }
                    case '?': {
                        return Operator.TERNARY;
                    }
                    case '>': {
                        if (this.position < len && this.string.charAt(this.position) == '=') {
                            ++this.position;
                            return Operator.GE;
                        }
                        return Operator.GT;
                    }
                    case '<': {
                        if (this.position < len) {
                            switch (this.string.charAt(this.position)) {
                                case '=': {
                                    ++this.position;
                                    return Operator.LE;
                                }
                                case '>': {
                                    ++this.position;
                                    return Operator.NE;
                                }
                            }
                        }
                        return Operator.LT;
                    }
                    case '=': {
                        if (this.position < len && this.string.charAt(this.position) == '=') {
                            ++this.position;
                            return Operator.EQ;
                        }
                        throw new RuntimeException("use == for equality at position " + this.position);
                    }
                    case '!': {
                        if (this.position < len && this.string.charAt(this.position) == '=') {
                            ++this.position;
                            return Operator.NE;
                        }
                        throw new RuntimeException("use != or <> for inequality at position " + this.position);
                    }
                    case '&': {
                        if (this.position < len && this.string.charAt(this.position) == '&') {
                            ++this.position;
                            return Operator.AND;
                        }
                        throw new RuntimeException("use && for AND at position " + this.position);
                    }
                    case '|': {
                        if (this.position < len && this.string.charAt(this.position) == '|') {
                            ++this.position;
                            return Operator.OR;
                        }
                        throw new RuntimeException("use || for OR at position " + this.position);
                    }
                    default: {
                        if (Character.isUnicodeIdentifierStart(ch)) {
                            final int start = this.position - 1;
                            while (this.position < len && Character.isUnicodeIdentifierPart(this.string.charAt(this.position))) {
                                ++this.position;
                            }
                            final String name = this.string.substring(start, this.position);
                            if (name.equals("pow")) {
                                return Operator.POW;
                            }
                        }
                        throw new RuntimeException("operator expected at position " + this.position + " instead of '" + ch + "'");
                    }
                }
            }
        }
        
        Object getOperand() {
            final int len = this.string.length();
            char ch = '\0';
            while (this.position < len && Character.isWhitespace(ch = this.string.charAt(this.position))) {
                ++this.position;
            }
            if (this.position == len) {
                throw new RuntimeException("operand expected but end of string found");
            }
            if (ch == '(') {
                ++this.position;
                return Tokeniser.START_NEW_EXPRESSION;
            }
            if (ch == '-') {
                ++this.position;
                return Operator.NEG;
            }
            if (ch == '+') {
                ++this.position;
                return Operator.PLUS;
            }
            if (ch == '.' || Character.isDigit(ch)) {
                return this.getBigDecimal();
            }
            if (!Character.isUnicodeIdentifierStart(ch)) {
                throw new RuntimeException("operand expected but '" + ch + "' found");
            }
            final int start = this.position++;
            while (this.position < len && Character.isUnicodeIdentifierPart(this.string.charAt(this.position))) {
                ++this.position;
            }
            final String substring;
            final String name = substring = this.string.substring(start, this.position);
            switch (substring) {
                case "abs": {
                    return Operator.ABS;
                }
                case "int": {
                    return Operator.INT;
                }
                case "ceil": {
                    return Operator.CEIL;
                }
                case "floor": {
                    return Operator.FLOOR;
                }
                default: {
                    return name;
                }
            }
        }
        
        private BigDecimal getBigDecimal() {
            final int len = this.string.length();
            final int start = this.position;
            char ch;
            while (this.position < len && (Character.isDigit(ch = this.string.charAt(this.position)) || ch == '.')) {
                ++this.position;
            }
            if (this.position < len && ((ch = this.string.charAt(this.position)) == 'E' || ch == 'e')) {
                ++this.position;
                if (this.position < len && ((ch = this.string.charAt(this.position)) == '+' || ch == '-')) {
                    ++this.position;
                }
                while (this.position < len && Character.isDigit(ch = this.string.charAt(this.position))) {
                    ++this.position;
                }
            }
            return new BigDecimal(this.string.substring(start, this.position));
        }
        
        @Override
        public String toString() {
            return this.string.substring(0, this.position) + ">>>" + this.string.substring(this.position);
        }
        
        static {
            Tokeniser.START_NEW_EXPRESSION = '(';
        }
    }
    
    public enum Operator
    {
        END(-1, 0, (String)null, (Type)null, (Type)null) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                throw new RuntimeException("END is a dummy operation");
            }
        }, 
        TERNARY(0, 3, "?", (Type)null, (Type)null) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.signum() != 0) ? value2 : value3;
            }
        }, 
        AND(0, 2, "&&", Type.BOOLEAN, Type.BOOLEAN) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.signum() != 0 && value2.signum() != 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        OR(0, 2, "||", Type.BOOLEAN, Type.BOOLEAN) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.signum() != 0 || value2.signum() != 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        GT(1, 2, ">", Type.BOOLEAN, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.compareTo(value2) > 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        GE(1, 2, ">=", Type.BOOLEAN, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.compareTo(value2) >= 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        LT(1, 2, "<", Type.BOOLEAN, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.compareTo(value2) < 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        LE(1, 2, "<=", Type.BOOLEAN, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.compareTo(value2) <= 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        EQ(1, 2, "==", Type.BOOLEAN, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.compareTo(value2) == 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        NE(1, 2, "!=", Type.BOOLEAN, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return (value1.compareTo(value2) != 0) ? BigDecimal.ONE : BigDecimal.ZERO;
            }
        }, 
        ADD(2, 2, "+", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1.add(value2);
            }
        }, 
        SUB(2, 2, "-", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1.subtract(value2);
            }
        }, 
        DIV(3, 2, "/", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1.divide(value2, MathContext.DECIMAL128);
            }
        }, 
        REMAINDER(3, 2, "%", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1.remainder(value2, MathContext.DECIMAL128);
            }
        }, 
        MUL(3, 2, "*", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1.multiply(value2);
            }
        }, 
        NEG(4, 1, "-", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1.negate();
            }
        }, 
        PLUS(4, 1, "+", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1;
            }
        }, 
        ABS(4, 1, " abs ", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1.abs();
            }
        }, 
        POW(4, 2, " pow ", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                try {
                    return value1.pow(value2.intValueExact());
                }
                catch (ArithmeticException ae) {
                    throw new RuntimeException("pow argument: " + ae.getMessage());
                }
            }
        }, 
        INT(4, 1, "int ", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return new BigDecimal(value1.toBigInteger());
            }
        }, 
        CEIL(4, 1, "ceil ", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return new BigDecimal(Math.ceil(value1.doubleValue()));
            }
        }, 
        FLOOR(4, 1, "floor ", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return new BigDecimal(Math.floor(value1.doubleValue()));
            }
        }, 
        NOP(4, 1, "", Type.ARITHMETIC, Type.ARITHMETIC) {
            @Override
            BigDecimal perform(final BigDecimal value1, final BigDecimal value2, final BigDecimal value3) {
                return value1;
            }
        };
        
        int precedence;
        int numberOfOperands;
        String string;
        Type resultType;
        Type operandType;
        
        private Operator(final int precedence, final int numberOfOperands, final String string, final Type resultType, final Type operandType) {
            this.precedence = precedence;
            this.numberOfOperands = numberOfOperands;
            this.string = string;
            this.resultType = resultType;
            this.operandType = operandType;
        }
        
        abstract BigDecimal perform(final BigDecimal p0, final BigDecimal p1, final BigDecimal p2);
    }
    
    public static class Operation
    {
        Type type;
        Operator operator;
        Object operand1;
        Object operand2;
        Object operand3;
        
        static Operation nopOperationfactory(final Object operand) {
            return new Operation(Operator.NOP.resultType, Operator.NOP, operand, null, null);
        }
        
        static Object unaryOperationfactory(final Operator operator, final Object operand) {
            validateOperandType(operand, operator.operandType);
            if (operand instanceof BigDecimal) {
                return operator.perform((BigDecimal)operand, null, null);
            }
            return new Operation(operator.resultType, operator, operand, null, null);
        }
        
        static Object binaryOperationfactory(final Operator operator, final Object operand1, final Object operand2) {
            validateOperandType(operand1, operator.operandType);
            validateOperandType(operand2, operator.operandType);
            if (operand1 instanceof BigDecimal && operand2 instanceof BigDecimal) {
                return operator.perform((BigDecimal)operand1, (BigDecimal)operand2, null);
            }
            return new Operation(operator.resultType, operator, operand1, operand2, null);
        }
        
        static Object tenaryOperationFactory(final Operator operator, final Object operand1, final Object operand2, final Object operand3) {
            validateOperandType(operand1, Type.BOOLEAN);
            validateOperandType(operand2, Type.ARITHMETIC);
            validateOperandType(operand3, Type.ARITHMETIC);
            if (operand1 instanceof BigDecimal) {
                return (((BigDecimal)operand1).signum() != 0) ? operand2 : operand3;
            }
            return new Operation(Type.ARITHMETIC, operator, operand1, operand2, operand3);
        }
        
        private static void validateOperandType(final Object operand, final Type type) {
            final Type operandType;
            if (operand instanceof Operation && (operandType = ((Operation)operand).type) != type) {
                throw new RuntimeException("cannot use " + operandType.name + " operands with " + type.name + " operators");
            }
        }
        
        private Operation(final Type type, final Operator operator, final Object operand1, final Object operand2, final Object operand3) {
            this.type = type;
            this.operator = operator;
            this.operand1 = operand1;
            this.operand2 = operand2;
            this.operand3 = operand3;
        }
        
        BigDecimal eval(final Map<String, BigDecimal> variables) {
            switch (this.operator.numberOfOperands) {
                case 3: {
                    return this.operator.perform(this.evaluateOperand(this.operand1, variables), this.evaluateOperand(this.operand2, variables), this.evaluateOperand(this.operand3, variables));
                }
                case 2: {
                    return this.operator.perform(this.evaluateOperand(this.operand1, variables), this.evaluateOperand(this.operand2, variables), null);
                }
                default: {
                    return this.operator.perform(this.evaluateOperand(this.operand1, variables), null, null);
                }
            }
        }
        
        private BigDecimal evaluateOperand(final Object operand, final Map<String, BigDecimal> variables) {
            if (operand instanceof Operation) {
                return ((Operation)operand).eval(variables);
            }
            if (!(operand instanceof String)) {
                return (BigDecimal)operand;
            }
            final BigDecimal value;
            if (variables == null || (value = variables.get(operand)) == null) {
                throw new RuntimeException("no value for variable \"" + operand + "\"");
            }
            return value;
        }
        
        @Override
        public String toString() {
            switch (this.operator.numberOfOperands) {
                case 3: {
                    return "(" + this.operand1 + this.operator.string + this.operand2 + ":" + this.operand3 + ")";
                }
                case 2: {
                    return "(" + this.operand1 + this.operator.string + this.operand2 + ")";
                }
                default: {
                    return "(" + this.operator.string + this.operand1 + ")";
                }
            }
        }
    }
    
    public static class Compiler
    {
        private final Tokeniser tokeniser;
        
        Compiler(final String expression) {
            this.tokeniser = new Tokeniser(expression);
        }
        
        Operation compile() {
            final Object expression = this.compile(null, null, 0, '\0', -1);
            if (expression instanceof Operation) {
                return (Operation)expression;
            }
            return Operation.nopOperationfactory(expression);
        }
        
        private Object compile(final Object preReadOperand, final Operator preReadOperator, final int nestingLevel, final char endOfExpressionChar, final int terminatePrecedence) {
            Object operand = (preReadOperand != null) ? preReadOperand : this.getOperand(nestingLevel);
            Operator operator = (preReadOperator != null) ? preReadOperator : this.tokeniser.getOperator(endOfExpressionChar);
            while (operator != Operator.END) {
                if (operator == Operator.TERNARY) {
                    final Object operand2 = this.compile(null, null, nestingLevel, ':', -1);
                    final Object operand3 = this.compile(null, null, nestingLevel, endOfExpressionChar, -1);
                    operand = Operation.tenaryOperationFactory(operator, operand, operand2, operand3);
                    operator = Operator.END;
                }
                else {
                    final Object nextOperand = this.getOperand(nestingLevel);
                    final Operator nextOperator = this.tokeniser.getOperator(endOfExpressionChar);
                    if (nextOperator == Operator.END) {
                        operand = Operation.binaryOperationfactory(operator, operand, nextOperand);
                        operator = Operator.END;
                        if (preReadOperator == null || endOfExpressionChar == '\0') {
                            continue;
                        }
                        this.tokeniser.pushBack(Operator.END);
                    }
                    else if (nextOperator.precedence <= terminatePrecedence) {
                        operand = Operation.binaryOperationfactory(operator, operand, nextOperand);
                        this.tokeniser.pushBack(nextOperator);
                        operator = Operator.END;
                    }
                    else if (operator.precedence >= nextOperator.precedence) {
                        operand = Operation.binaryOperationfactory(operator, operand, nextOperand);
                        operator = nextOperator;
                    }
                    else {
                        operand = Operation.binaryOperationfactory(operator, operand, this.compile(nextOperand, nextOperator, nestingLevel, endOfExpressionChar, operator.precedence));
                        operator = this.tokeniser.getOperator(endOfExpressionChar);
                        if (operator != Operator.END || preReadOperator == null || endOfExpressionChar == '\0') {
                            continue;
                        }
                        this.tokeniser.pushBack(Operator.END);
                    }
                }
            }
            return operand;
        }
        
        private Object getOperand(final int nestingLevel) {
            Object operand = this.tokeniser.getOperand();
            if (operand == Tokeniser.START_NEW_EXPRESSION) {
                operand = this.compile(null, null, nestingLevel + 1, ')', -1);
            }
            else if (operand instanceof Operator) {
                return Operation.unaryOperationfactory((Operator)operand, this.getOperand(nestingLevel));
            }
            return operand;
        }
    }
}
