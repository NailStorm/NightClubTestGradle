import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class HumanFactory {

    public int getHumanCount(int min, int max){
        int peopleNum = ThreadLocalRandom.current().nextInt(min, max);
        return peopleNum;
    }

    /**
     * Заполняем массив именами посетителей.
     */
    public List<String> getHumansNames(int humanCount) throws IOException {
        List<String> humans = new ArrayList<>();

        for (int i=1; i<=humanCount; i++)
        {
            humans.add(getHumanNameFromApi());
        }

        return humans;
    }

    /**
     * Генерируем рандомный набор скилов для каждого танцора.
     * У каждого есть хотя бы 1 скилл, чтобы не приходили просто побухать.
     */
    public String getHumanSkills(List<String> skills){
        String personalSkills = "";

        Set<Integer> randomSkills = new HashSet<Integer>();
        Random rr = new Random();
        while (randomSkills.size() < ThreadLocalRandom.current().nextInt(1, skills.size()+1)) {
            randomSkills.add(rr.nextInt(skills.size()));
        }

        List<Integer> skillsId = new ArrayList<>();
        skillsId.addAll(randomSkills);

        for(int i=0; i<skillsId.size(); i++)
        {personalSkills = personalSkills + skills.get(skillsId.get(i))+ ", ";}

        return personalSkills;
    }

    /**
     * Генератор имен.
     * Нашел в гугле генератор, у которого есть апи.
     */
    public String getHumanNameFromApi() throws IOException {
        String name = null;
        String randusUrl = "https://randus.org/api.php";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(randusUrl)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        response.close();

        JSONObject json = new JSONObject(result);
        name = (String) json.get("fname") + " " + (String) json.get("lname");

        return name;
    }

}