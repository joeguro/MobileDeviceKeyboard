import java.util.*;

public class AutocompleteProvider {
    public List<Candidate> candidates;

    AutocompleteProvider() {
	candidates = new ArrayList<Candidate>();
    }
    
    public List<Candidate> getWords(String fragment) {
	ArrayList<Candidate> retList = new ArrayList<Candidate>();
	for(int i = 0; i < candidates.size(); i++) {
	    if(candidates.get(i).getWord().length() < fragment.length()) {
		continue;
	    }
	    if((candidates.get(i).getWord().substring(0,fragment.length())).equals(fragment)) {
		boolean added = false;
		for(int j = 0; j < retList.size(); j++) {
		    if(candidates.get(i).getConfidence() >= retList.get(j).getConfidence()) {
			retList.add(j, candidates.get(i));
			added = true;
			break;
		    }
		}
		if(!added) {
		    retList.add(candidates.get(i));
		}
	    }
	}
	return retList;
    }
    public void train(String passage) {
	String[] words = passage.split("\\W+");
	for(String s : words) {
	    s = s.toLowerCase();
	    boolean found = false;
	    for(int i = 0; i < candidates.size(); i++) {
		if(candidates.get(i).getWord().equals(s)) {
		    candidates.get(i).incrementConfidence();
		    found = true;
		    break;
		}
	    }
	    if(!found) {
		candidates.add(new Candidate(s));
	    }
	}
    }
    public static void main(String[] args) {
	AutocompleteProvider data = new AutocompleteProvider();
	Scanner scan = new Scanner(System.in);
	String fragment = "";
	boolean input = false;
	String message = ">Type \"train\" to train the algorithm with a new passage, \"input\" to get word reccomendations for a word fragment, or \"exit\" to stop\n>";
	System.out.print(message);
	String response = scan.nextLine();
	while(!response.equals("exit")) {
	    if(response.equals("train")) {
		System.out.print(">");
		data.train(scan.nextLine());
		if(input) {
		    List<Candidate> temp = data.getWords(fragment);
		    if(temp.size() > 0) {
			System.out.print(">input: " + fragment + " --> \"" + temp.get(0).getWord() + "\" (" + temp.get(0).getConfidence() + ")");
			for(int i = 1; i < temp.size(); i++) {
			    System.out.print(", \"" + temp.get(i).getWord() + "\" (" + temp.get(i).getConfidence() + ")");
			}
		    } else {
			System.out.print(">input: " + fragment + " --> No recommendations");
		    }
		    System.out.print("\n");
		}
	    } else if(response.equals("input")) {
		System.out.print(">");
		input = true;
		fragment = scan.nextLine();
		List<Candidate> temp = data.getWords(fragment);
		if(temp.size() > 0) {
		    System.out.print(">input: " + fragment + " --> \"" + temp.get(0).getWord() + "\" (" + temp.get(0).getConfidence() + ")");
		    for(int i = 1; i < temp.size(); i++) {
			System.out.print(", \"" + temp.get(i).getWord() + "\" (" + temp.get(i).getConfidence() + ")");
		    }
		} else {
		    System.out.print(">input: " + fragment + " --> No recommendations");
		}
		System.out.print("\n");
	    }
	    if(!response.equals("exit")) {
		System.out.print(message);
	    }
	    response = scan.nextLine();
	}
    }
}

