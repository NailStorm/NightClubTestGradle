import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import static java.lang.System.out;

/**
 * Used IntelliJ IDEA 2018.3, gradle, and Java 11
 *
 * TASK:
 * В ночной клуб приходят мальчики и девочки. Каждый персонаж, пришедший в ночной клуб
 * может обладать разными навыками танцевать под разную музыку.
 * Когда играет Rnb на танцполе танцуют те, кто танцуют хип-хоп, рнб.
 * Когда играет Electrohuse на танцполе танцуют те, кто танцуют Electrodance.
 * Когда играет Поп-музыка танцуют те кто умеют танцевать под поп-музыку.
 * Если человек не умеет танцевать под данную музыку, он идет в бар и пьет водку.
 * Необходимо эмулировать ночной клуб с произвольным количеством разных персонажей.
 * В то время, когда они слышат музыку, персонажи должны соответствующим образом себя вести:
 * танцевать или пить водку в баре.
 * Поведение персонажей следует выводить на экран текстом.
 * Музыка меняется в клубе каждые 5 секунд автоматически.
 *
 * COMMENT:
 * Я намеренно игнорирую гендерную пренодлежность персонажей и не использую в коде,
 * т.к. это не влияет на выполнение алгоритма.
 * Ограничил максимальное количество людей, чтобы не перегружать вывод,
 * минимальное чтобы алгоритм в любом случае что-то выводил.
 * Так же ввел ограничение по длинне ночи, чтобы не делать бесконечный цикл.
 */

public class Main {

    public static int SONG_DURATION = 5;
    public static int NIGHT_DURATION = 30;
    public static int MAX_PEOPLE = 10;
    public static int MIN_PEOPLE = 5;
    public static List<String> SKILLS = Arrays.asList("hip-hop", "rnb", "electronic", "pop");

    public static void main(String[] args) throws InterruptedException, IOException {
        HumanFactory factory = new HumanFactory();

        int humanCount = factory.getHumanCount(MIN_PEOPLE, MAX_PEOPLE);
        out.println("\nЛюди в клубе:");

        /**
         * Генерируем посетителей
         */
        String[][] humans = new String[humanCount][2];
        String[] nam = factory.getHumansNames(humanCount).toArray(new String[0]);
        for (int i=0;i<humanCount; i++){
            humans[i][0] = nam[i];
            humans[i][1] = factory.getHumanSkills(SKILLS);
        }

        for (int i=0;i<humanCount;i++){
            out.println(i+1+". "+humans[i][0]+", умеет танцевать: "+humans[i][1]);
        }

        /**
         * Теперь танцуем
         */
        for (int T=0; T<=NIGHT_DURATION; T=T+SONG_DURATION){
            Random music = new Random();
            String randomMusic = SKILLS.get(music.nextInt(SKILLS.size()));
            out.println("\nИграет: "+ randomMusic);

            for (int i=0;i<humanCount;i++){
                boolean mySong;
                mySong = humans[i][1].contains(randomMusic);

                if (mySong == true) {
                    out.println(humans[i][0] + " танцует!");
                } else if (mySong == false) {
                    out.println(humans[i][0] + " идет в бар за водкой...");
                }
            }

            TimeUnit.SECONDS.sleep(SONG_DURATION);
        }
    }
}

