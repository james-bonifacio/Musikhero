
public class Chords {
	
	public static boolean isChord(String c){
		
		boolean isTrue = true;
		String check;
		
		for (int i = 0; i < c.length(); i++){
			
			check = Character.toString(c.charAt(i));
			
			if (!(check.equals(" ")) && !(check.equals("\r")) && !(check.equals("m")) && !(check.equals("#")) && !(check.equals("7")) && chordToInt(check) == -1){
				
					isTrue = false;
			}
		}
		return isTrue;
		
	}
	
	
	public static String transposeChord(String chords, String from, String to){
		String conv = "";
		
		String toConv = chords + "   ";
		
		for (int i = 0; i < toConv.length() - 3; i++){
			
			char c = toConv.charAt(i);
			String chord = " ";
			
				if (c != ' '){
					
					chord = Character.toString(c);
					
					if (toConv.charAt(i + 1) == '#'){
						
						chord = chord + "#";
						i++;
					}
					
					chord = add(chord, from, to);
					
					i++;
					
					while (toConv.charAt(i) != ' ' ){
						
						chord = chord + Character.toString(toConv.charAt(i));
						i++;
					}
					
					chord = chord + " ";
				}
				
			conv = conv + chord;
		
		
		}
		
		return conv;
	}//end transposeChord
	
	public static String add(String c, String from, String to){
		String chord = c;
		int increase = chordToInt(to) - chordToInt(from);
		
		int num = chordToInt(c);
		
		num = num + increase;
		
		if (num > 11){
			num = (num % 11) - 1;
		}
		
		if (num < 0){
			num = 12 + num;
		}
		
		chord = intToChord(num);
		
		return chord;
	}
	
	public static int chordToInt(String s){
		int n = -1;
		
		String[] chords = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		
		for (int i = 0; i<12;i++){
			if (s.equals(chords[i])){
				n = i;
			}
		}
		return n;
	}
	
	public static String intToChord(int i){
		String n = "";
		
		String[] chords = {"A", "A#", "B", "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#"};
		
		n = chords[i];
		return n;
	}
}
