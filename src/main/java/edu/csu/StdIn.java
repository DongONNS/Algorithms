package edu.csu;

import java.util.*;
import java.util.regex.Pattern;

public final class StdIn {
    private static final String CHARSET_NAME ="utf-8";

    private static final Locale LOCALE = Locale.US;

    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\{javaWhitespace}+");

    private static final Pattern EMPTY_PATTER =Pattern.compile("");

    private static final Pattern EVERYTHING_PATTER =Pattern.compile("\\A");

    private static Scanner scanner;

    private StdIn(){}

    public static boolean isEmpty(){
        return !scanner.hasNext();
    }

    public static boolean hasNextLine(){
        return scanner.hasNextLine();
    }

    public static boolean hasNextChar(){
        scanner.useDelimiter(EMPTY_PATTER);
        boolean result =scanner.hasNext();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }


    public static String readLine(){
        String line = null;
        try{
            line = scanner.nextLine();
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }
        return line;
    }

    public static char readChar(){
        try{
            scanner.useDelimiter(EMPTY_PATTER);
            String ch =scanner.next();
            assert ch.length()==1:"inteval (Std)In.readChar occur a error";
            scanner.useDelimiter(WHITESPACE_PATTERN);
            return ch.charAt(0);
        }catch(NoSuchElementException e){
            throw new NoSuchElementException("there is no element for reading");
        }
    }

    public static String readAll(){
        if (!scanner.hasNextLine()) return "";
        String result =scanner.useDelimiter(EVERYTHING_PATTER).next();
        scanner.useDelimiter(WHITESPACE_PATTERN);
        return result;
    }

    public static String readString(){
        try{
            return scanner.next();
        }catch(NoSuchElementException e ){
            throw new NoSuchElementException("there is no String input");
        }
    }

    public static int readInt(){
        try{
            return scanner.nextInt();
        } catch (InputMismatchException e){
            throw new InputMismatchException("the next token is not a int");
        } catch (NoSuchElementException f){
            throw new NoSuchElementException("no more tokens are available");
        }
    }

    public static double readDouble(){
        try{
            return scanner.nextDouble();
        } catch (InputMismatchException e){
            throw new InputMismatchException("the next token is not a double");
        } catch (NoSuchElementException f){
            throw new NoSuchElementException("no more tokens are available");
        }
    }

    public static float readFloat(){
        try{
            return scanner.nextFloat();
        } catch (InputMismatchException e){
            throw new InputMismatchException("the next token is not a float");
        } catch (NoSuchElementException f){
            throw new NoSuchElementException("no more tokens are available");
        }
    }

    public static float readLong(){
        try{
            return scanner.nextLong();
        } catch (InputMismatchException e){
            throw new InputMismatchException("the next token is not a long");
        } catch (NoSuchElementException f){
            throw new NoSuchElementException("no more tokens are available");
        }
    }
    public static float readShort(){
        try{
            return scanner.nextLong();
        } catch (InputMismatchException e){
            throw new InputMismatchException("the next token is not a short");
        } catch (NoSuchElementException f){
            throw new NoSuchElementException("no more tokens are available");
        }
    }

    public static float readByte(){
        try{
            return scanner.nextByte();
        } catch (InputMismatchException e){
            throw new InputMismatchException("the next token is not a byte");
        } catch (NoSuchElementException f){
            throw new NoSuchElementException("no more tokens are available");
        }
    }

    public static boolean readBoolean(){
        try {
            String token =readString();
            if ("true".equalsIgnoreCase(token)) return true;
            if ("false".equalsIgnoreCase(token)) return false;
            if ("1".equals(token)) return true;
            if ("0".equals(token)) return false;
            throw new InputMismatchException("the next token is not suitable");
        } catch (NoSuchElementException f){
            throw new NoSuchElementException("no more tokens are available");
        }
    }

    public static String[] readAllString(){
        String[] tokens = WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length()>0){
            return tokens;
        }

        String[] decapitokens =new String[tokens.length-1];
        for (int i=0;i<decapitokens.length-1;i++){
            decapitokens[i] = tokens[i+1];
        }
        return decapitokens;
    }

    public static String[] readAllLines(){
        List<String> lines = new ArrayList<String>( );
        while(hasNextLine()){
            lines.add(readLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static int[] readAllInts(){
        String[] fields = readAllString();
        int[] vals = new int[fields.length];
        for (int i=0;i<fields.length;i++){
            vals[i] = Integer.parseInt(fields[i]);
        }
        return vals;
    }

    public static long[] readAllLongs(){
        String[] fields = readAllString();
        long[] vals = new long[fields.length];
        for (int i=0;i<fields.length;i++){
            vals[i] = Long.parseLong(fields[i]);
        }
        return vals;
    }

    public static double[] readAllDoubles(){
        String[] fields = readAllString();
        double[] vals = new double[fields.length];
        for (int i=0;i<fields.length;i++){
            vals[i] = Double.parseDouble(fields[i]);
        }
        return vals;
    }

    static {
        resync();
    }

    public static void resync(){
        setScanner(new Scanner(new java.io.BufferedInputStream(System.in),CHARSET_NAME));
    }

    public static void setScanner(Scanner scanner){
        StdIn.scanner = scanner;
        StdIn.scanner.useLocale(LOCALE);
    }

}
