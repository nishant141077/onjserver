/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package management;

import entities.Problem;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author nishant
 */
public class Sorter {

    static void sortByName(List<Problem> problemsList, int order) {
        Collections.sort(problemsList, new Comparator<Problem>(){
            @Override
            public int compare(Problem p1, Problem p2) {
                int compareVal = p1.name.compareTo(p2.name);
                if(compareVal < 0) return -1;
                else if(compareVal > 0) return 1;
                else return 0;
            }
        });
    }
    
}
