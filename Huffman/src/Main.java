import java.io.IOException;
import java.nio.file.*;
import java.util.Vector;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.nio.file.Files;
import java.io.IOException;
import java.io.FileWriter;

//to order priority queue ascending and keep lowest at first
class compareP implements Comparator<Node>
{      public int compare(Node first,Node second)
{
    return first.P - second.P;

}
}
public class Main{
    //reading files "input " as string
    public static String readFile(String name) throws IOException {
        String input = "";
        input = new String(Files.readAllBytes(Paths.get(name)));
        return input;
    }
    //Assign codes to symbols
    static Vector<Dictionary>  dictionary = new Vector<>(); //store symbol and its code to replace symbol with code if needed
    public static String dict(Node curr, String code )
    {
        Dictionary obj = new Dictionary();
        if (curr.left == null && curr.right == null  )
        {

            obj.symbol= curr.x;
            obj.code= code;
            if(!(dictionary.contains(obj)))
            {
                dictionary.add(obj);
            }

            return curr.x + " "+ code;

        }

      return  dict(curr.right, code + "0") +" ," +dict(curr.left, code + "1");

    }

    public static void main(String[] args) throws Exception
    {
        String input = readFile(System.getProperty("user.home") + "/Desktop/Huffman/input.txt");
        String compressed="" ;
        String decompressed="" ;
        int charCount=0;
        Vector<Integer>  p = new Vector<>(); //store probalities
        Vector<Character>  inputChars = new Vector<>(); //store symbols

        Node root =null;
        PriorityQueue<Node> Tree = new PriorityQueue<>(inputChars.size()+1, new compareP());
        //int originalSize=0 , comSize=0;

        //putting every single symbol in vector
        for(int i = 0;i<input.length();i++)
        {

            if(!(inputChars.contains(input.charAt(i))))
            {
                inputChars.add(input.charAt(i));
            }
        }
//counting number of appearnce for each symbol
        for(int i=0;i<inputChars.size();i++){
            for(int j=0;j<input.length();j++){
                if(inputChars.get(i)==input.charAt(j)){
                    charCount++;
                }
            }
            p.add(charCount);
            charCount=0;
        }
//representing leaves of the tree
        for (int i = 0; i < inputChars.size(); i++)
        {
            Node N = new Node();
            N.x = inputChars.get(i);
            N.P = p.get(i);
            N.left = null;
            N.right = null;
            Tree.add(N);
        }

        while (Tree.size() > 1)
        {
            Node fChild = Tree.peek(); //lowest element
           Tree.poll();
            Node sChild = Tree.peek(); //second lowest element
            Tree.poll();
            Node parent = new Node();
            parent.P = fChild.P +sChild.P; //merge lowest into subgroup
            parent.x = ' ';
            parent.left = fChild;
            parent.right =sChild;
            Tree.add(parent);
            root = parent;

        }
        System.out.println("Dictionary : ");
        System.out.println(dict(root,""));
        dictionary.clear();
        //write the dictionary in file
        FileWriter writeDict = new FileWriter("dictionary.txt");
        writeDict.write(dict(root,""));
        writeDict.close();
       //Compressed output
        for (int i=0 ; i <input.length();i++)
        {
            for (int j=0; j< dictionary.size();j++) {
                if (input.charAt(i) ==dictionary.get(j).symbol)
                {
                    compressed =compressed+dictionary.get(j).code;
                }
            }

        }
        System.out.println("Compressed: ");
        System.out.println(compressed);
        FileWriter writeCompressed = new FileWriter("compressed.txt");
        writeCompressed.write(compressed);
        writeCompressed.close();
        //************Decompression****************
        String search ="";
       for (int i=0 ; i <compressed.length();i++)
        {
            search=search+compressed.charAt(i);

            for (int j=0; j< dictionary.size();j++) {

                if (search.equals(dictionary.get(j).code))
                {
                    decompressed =decompressed+dictionary.get(j).symbol;
                    search="";
                }

            }
        }

        System.out.println("deCompressed: ");
        System.out.println(decompressed);
       //System.out.println("dict:");
      //  for (int i = 0; i < dictionary.size(); i++) {
          //  dictionary.get(i).display();


       // }

    }


}
