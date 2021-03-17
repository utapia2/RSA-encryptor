
/*
Name: Ulisses Tapia
Class: CSC 447
#Date: 2/21/2018
File Name: RSA.java
Description: encrypts a message inputted by the user based off of two prime numbers inputted by the user as well.
Example output:
Enter the first prime number: 641
Enter the second prime number: 19
Enter your message to be encrypted: It was about that time that I noticed this girl scout was about 8 stories tall and was a crustacean from the paleozoic era
---------------------------
P: 641
Q: 19
n: 12179
phi(n): 11520
e: 7
d: 6583
Starting message: It was about that time that I noticed this girl scout was about 8 stories tall and was a crustacean from the paleozoic era
Encrypted message: 6632807255696681185171452556118512890178469372807255628072284118512807255628077943727570925562807228
41185128072556663255610976178428077941868570911929255628072284794714525569736794809411429255671451868178469372807255696
68118517145255611851289017846937280725565965255671452807178480947945709714525562807118511142911429255611851109761192925
569668118517145255611851255618688094693771452807118511868570911851109762556549880941784372725562807228457092556842211851
114295709178421551784794186825565709809411851
Decrypted message: It was about that time that I noticed this girl scout was about 8 stories tall and was a crustacean from the paleozoic era

*/

import java.util.Scanner;
import java.math.BigInteger;

public class RSA{
    public static void main(String[] args){


        //ask user for input of the two prime numbers
        Scanner input = new Scanner(System.in);
        System.out.print("It's time for RSA, we're going to need two prime numbers\n");
        System.out.print("Enter the first prime number: ");
        int P = input.nextInt();

        if (!isPrime(P)) {
            System.out.println("Sorry, P must be a prime number.");
            System.exit(0);
        }
        System.out.print("Enter the second prime number: ");
        int Q = input.nextInt();
        if (!isPrime(Q)) {
            System.out.println("Sorry, Q must be a prime number.");
            System.exit(0);
        }

        System.out.print("Enter your message to be encrypted: ");
        Scanner input1 = new Scanner(System.in);
        String mess = input1.nextLine();


        int n = P*Q;
        //converted to BigInteger because there's a function that make calculations easy
        BigInteger bign = BigInteger.valueOf(n);



        int totient = ((P-1)*(Q-1));
        //e cant be 1 or 2(even)
        int e = 3;

        //loop until 1 < e < totient, increment until the GCD of e and the totient is 1
        while(e < totient){

            //make e and totient into BigIntegers for gcd
            BigInteger a = BigInteger.valueOf(e);
            BigInteger b = BigInteger.valueOf(totient);


            if((b.gcd(a)).intValue() == 1)
                break;
            else
                e++;
        }
        //making them BigIntegers
        //e
        BigInteger Pubkey = BigInteger.valueOf(e);

        BigInteger bigtotient = BigInteger.valueOf(totient);
        //d
        BigInteger Privkey = Pubkey.modInverse(bigtotient);




        //encrypted message as an int array
        int encryption[] = new int[mess.length()];


        String decryptedMess = "";

        for(int i =0; i < mess.length(); i++) {
            int val = mess.charAt(i);
            //convert that char value to a BigInteger
            BigInteger bigVal = BigInteger.valueOf(val);
            BigInteger encrypt = bigVal.modPow(Pubkey, bign);
            encryption[i] = encrypt.intValue();

            //decrypt the encrypted value
            BigInteger decrypt = encrypt.modPow(Privkey, bign);
            int decryption = decrypt.intValue();
            //convert value to char, and append to message
            decryptedMess += (char)decryption;
        }



        System.out.println("---------------------------");



        System.out.println("P: " + P);
        System.out.println("Q: " + Q);
        System.out.println("n: " + n);
        System.out.println("phi(n): " + totient);
        System.out.println("e: " + Pubkey);
        System.out.println("d: " + Privkey);
        System.out.println("Starting message: " + mess);
        System.out.print("Encrypted message: ");
        print(encryption);
        System.out.println("Decrypted message: " + decryptedMess);


    }




    //function that checks if the number entered is a prime number
    public static boolean isPrime(int n){

        if (n%2==0)
            return false;

        for(int i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }//end isPrime

    //prints the array
    public static void print(int[]array){
        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]);

        }
        System.out.println();
    }
}//end RSA