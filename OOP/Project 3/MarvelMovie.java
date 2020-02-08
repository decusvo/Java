import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MarvelMovie {
	
	static int index_pointer = 0; // global pointer for the ArrayList indexes which contain node data and node types.
	
	// Method requests user's input and returns a true if user's answer is equal to "y" , otherwise false is returned.
	static boolean askYesNo(String question) {
		Scanner scanner = new Scanner(System.in);
		System.out.println(question+" [y/N]");
		String temp = scanner.nextLine();
		String answer = temp;
		// if yes, next node is the left child.
		// if no , next node is the right child.
		if ( answer.equals("y")) {
			return true;
		} else {
			return false;
		}
	}
	
	// method takes in two ArrayLists. One contains string values , the other one contains type of the node of the matching index.
	// Tree is constructed in preorder, the root node is returned at the very end.
	public static TreeNode treeConstruction(ArrayList<Character> listTypes,ArrayList<String> list) { 		
		Character current_node_type = listTypes.get(index_pointer);
		String current_node_payload = list.get(index_pointer);
		index_pointer++;

		if (current_node_type == 'N') {
			TreeNode node = new TreeNode(current_node_payload,treeConstruction(listTypes,list),treeConstruction(listTypes,list));
			return node;
		} else {
			TreeNode leaf_node = new TreeNode(current_node_payload);
			return leaf_node;
		}
	}
	
	// Method takes in the root node and a bufferedwriter instance.
	// Traverses through the tree in preorder recursively.
	public static void preOrderTraverse(TreeNode node,BufferedWriter bw) throws IOException {
		if (node != null) {
			//System.out.println(node.getValue());
			bw.write(node.getValue());
			bw.newLine();
			preOrderTraverse(node.left,bw);
			preOrderTraverse(node.right,bw);
		}
	}
	
	// method asking user to enter the name of the preferred movie.Returns the name of the movie as string.
	static String preferredMovieName() {
		Scanner scanner2 = new Scanner(System.in);
		System.out.println("What would you prefer instead?");
		String temp = scanner2.nextLine();
		String answer = temp;
		return answer;
	}
	
	// method asks the user for a question that would describe the preferred movie of user's choice. Returns the question as string.
	static String preferredMovieQuestion(String wrongMovieName,String correctMovieName) {
		Scanner scanner3 = new Scanner(System.in);
		System.out.println("Tell me a question that distinguishes "+correctMovieName+" from "+wrongMovieName);
		String temp = scanner3.nextLine();
		String answer = temp;
		return answer;	
	}
	
	// Method that takes the root node from the main method, asks the user questions and traverses the tree accordingly.
	static boolean interaction(TreeNode startingNode) {
		boolean answer = askYesNo(startingNode.getValue());
		boolean playAgainQuestionFlag = false;
		// last recursion instance gets the correct boolean but as the recursion unwinds the boolean is changed back to default value and returned.
		if (answer == true) {
            // Yes suggestion is given.
			if(startingNode.left.isLeaf() == true) {
				System.out.println("Perhaps you would like: "+startingNode.left.getValue());
				boolean satisfactionAnswer = askYesNo("Is this satisfactory ? [y/N]");
				if (satisfactionAnswer == true) {
					playAgainQuestionFlag = askYesNo("Do you want to play again ? [y/N]");
				} else {
					String movieName = preferredMovieName();
					String movieQuestion = preferredMovieQuestion(startingNode.left.getValue(),movieName);
					TreeNode oldC = startingNode.left;
					TreeNode newC = new TreeNode(movieName);
					TreeNode newQ = new TreeNode(movieQuestion,newC,oldC);
					startingNode.left = newQ;
					playAgainQuestionFlag = askYesNo("Do you want to play again ?");
				}
			} else {
				// Another question is asked.
				playAgainQuestionFlag = interaction(startingNode.left);
			} 
		} else { 
		// no suggestion is given.
			if(startingNode.right.isLeaf() == true) {
				System.out.println("Perhaps you would like: "+startingNode.right.getValue());
				boolean satisfactionAnswer = askYesNo("Is this satisfactory?");
				if (satisfactionAnswer == true) {
					playAgainQuestionFlag = askYesNo("Do you want to play again?");
				}
				else {
				String movieName = preferredMovieName();
				String movieQuestion = preferredMovieQuestion(startingNode.right.getValue(),movieName);
				TreeNode oldC = startingNode.right;
				TreeNode newC = new TreeNode(movieName);
				TreeNode newQ = new TreeNode(movieQuestion,newC,oldC);
				startingNode.right = newQ;
				playAgainQuestionFlag = askYesNo("Do you want to play again?");
				}
			} else {
				// Another question.
				playAgainQuestionFlag = interaction(startingNode.right);
			} 
		}
		return playAgainQuestionFlag;
	}

	// main method.
	public static void main(String args[]) throws IOException {
		FileReader reader = null;
		TreeNode root = null;
		System.out.println("Welcome to the Marvel Movie Selector!");
		try {
			// Reading lines from the text file and storing them within an ArrayList.
			reader = new FileReader("suggestions.txt");
			BufferedReader br1 = new BufferedReader(reader);
			
			ArrayList<String> preOrderLines= new ArrayList<String>();
			ArrayList<Character> preOrderLinesTypes = new ArrayList<Character>();
			String line;
			while((line = br1.readLine()) != null) {
			    preOrderLines.add(line);
			    if (line.indexOf("?")!=-1) {
			    	// doesn't contain question mark.
			    	preOrderLinesTypes.add('N');
			    } else {
			    	preOrderLinesTypes.add('L');
			    }
			}
			
			root = treeConstruction(preOrderLinesTypes,preOrderLines);
			br1.close();
			reader.close();	
		
		// if exception is thrown for any reason, loading stops and default tree is populated.
		} catch (Exception e) {
			System.out.println("file not found.Failed to load or read file.");
			// Populating the default tree.
			TreeNode temp = new TreeNode("Do you like captains?");
			root = temp;
			temp.left = new TreeNode("Would you like a captain who’s better than superman?");
			temp.right = new TreeNode("Iron Man");
			temp = temp.left;
			temp.left = new TreeNode("Captain Marvel");
			temp.right = new TreeNode("Captain America");
		} 
		// code that asks the user if he wants to save the current instance of the tree.
		finally {
			boolean playAgainGameFlag = false;
			do {
			playAgainGameFlag = interaction(root);
			} while (playAgainGameFlag  == true);
			File filename = new File("suggestions.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
			if (askYesNo("Do you want to save the current tree?")) {
			System.out.println();
			preOrderTraverse(root,bw);
			}
			bw.close();
		}
	}
}

