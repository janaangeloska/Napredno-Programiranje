/*Да се дефинира класа Risk со единствен метод int processAttacksData (InputStream is).
Методот од влезен поток ги чита информациите за извршените напади на еден играч врз другите играчи во стратешката игра Ризик. За секој поединечен напад информациите ќе се дадени во посебен ред и ќе бидат во следниот формат:
X1 X2 X3;Y1 Y2 Y3, каде што X1, X2 и X3 се броеви добиени со фрлање на 3 коцки (број од 1-6) на напаѓачот, а Y1, Y2 и Y3 се броеви добиени со фрлање на 3 коцки (број од 1-6) за одбрана. Како резултат методот треба да го врати бројот на целосно успешнo извршени напади. 
Еден напад се смета дека е целосно успешен доколку сите коцки фрлени од напаѓачот имаат број поголем од бројот на фрлените коцки на нападнатиот (најголемиот број на фрлените коцки на напаѓачот е поголем од најголемиот број на фрлените коцки на нападнатиот и соодветно за сите останати обиди (редоследот на фрлените коцки не игра улога). 
Пример влезот: 5 3 4; 2 4 1 се смета за целосно успешен напад бидејќи најголемата вредност на напаѓачот (5) е поголема од најголемата вредност на нападнатиот (4), втората најголема вредност на напаѓачот (4) е поголема од втората најголема вредност на нападнатиот (2), како и третата најголема вредност (3) на напаѓачот е поголема од третата најголемата вредност на нападнатиот (1).*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Round{
    List<Integer> attacker;
    List<Integer> defender;

    public Round(List<Integer> attacker, List<Integer> defender) {
        this.attacker = attacker;
        this.defender = defender;

    }

    public Round(String line) {
        String[] parts = line.split(";");
    this.attacker=parseDice(parts[0]);
    this.defender=parseDice(parts[1]);
    }

    private List<Integer> parseDice(String i){
        return Arrays.stream(i.split("\\s+"))
                .map(dice->Integer.parseInt(dice))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Round{" +
                "attacker=" + attacker +
                ", defender=" + defender +
                '}';
    }

    public boolean hasAttackerWon(){
        for (int i = 0; i < attacker.size(); i++) {
            if(attacker.get(i)<=defender.get(i)){
                return false;
            }
        }
        return true;
    }

}

class Risk {

    public int processAttacksData(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int result = (int) br.lines()
                .map(line -> new Round(line))
                .filter(round -> round.hasAttackerWon())
                .count();

        br.close();
        return result;
    }
}

    public class RiskTester {
        public static void main(String[] args) {

            Risk risk = new Risk();

            try {
                System.out.println(risk.processAttacksData(System.in));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
