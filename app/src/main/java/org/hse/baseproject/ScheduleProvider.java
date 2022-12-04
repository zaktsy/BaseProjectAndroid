package org.hse.baseproject;

import android.net.Uri;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class ScheduleProvider {

    private final HttpRequestSender requestSender = new HttpRequestSender();
    private final TimeIntervalProvider timeIntervalProvider = new TimeIntervalProvider();

    public List<ScheduleItem> getSchedule(ScheduleMode mode, ScheduleType type, String name) {

        String requestString = constructRequestString(mode, type, name);

        try {
            String response = (requestSender.get(requestString)).get();
            Gson gson = new Gson();
            Lesson[] lessons = gson.fromJson(response, Lesson[].class);
            ArrayList<Lesson> lessonsList = new ArrayList<>(Arrays.asList(lessons));

            return transformLessonsToScheduleItems(lessonsList);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String constructRequestString(ScheduleMode mode, ScheduleType type, String name) {
        String actor = getActor(mode);
        String actorId = getActorId(name, actor);
        TimeInterval timeInterval = timeIntervalProvider.getTimeInterval(type);

        String language = Locale.getDefault().getLanguage();
        int languageCode = 1;
        if (language.equals(Locale.ENGLISH.getLanguage())) languageCode = 2;

        String modeToSearch = ApiRoutes.person;
        if (mode == ScheduleMode.STUDENT)
            modeToSearch = ApiRoutes.student;

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ApiRoutes.apiHost)
                .appendPath(ApiRoutes.api)
                .appendPath(ApiRoutes.schedule)
                .appendPath(modeToSearch)
                .appendPath(actorId)
                .appendQueryParameter(ApiRoutes.start, timeInterval.getStartTime())
                .appendQueryParameter(ApiRoutes.finish, timeInterval.getFinishTime())
                .appendQueryParameter(ApiRoutes.language, String.valueOf(languageCode));

        return builder.build().toString();
    }

    private String getActor(ScheduleMode mode) {
        switch (mode) {
            case STUDENT:
                return ApiRoutes.student;
            case TEACHER:
                return ApiRoutes.person;
        }

        return "";
    }

    private String getActorId(String name, String actor) {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(ApiRoutes.apiHost)
                .appendPath(ApiRoutes.api)
                .appendPath(ApiRoutes.search)
                .appendQueryParameter(ApiRoutes.term, name)
                .appendQueryParameter(ApiRoutes.type, actor);

        String requestString = builder.build().toString();

        try {
            String response = (requestSender.get(requestString)).get();
            JSONArray responseJson = new JSONArray(response);
            JSONObject jsonInnerArray = (JSONObject) responseJson.getJSONObject(0);

            return jsonInnerArray.getString("id");
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<ScheduleItem> transformLessonsToScheduleItems(ArrayList<Lesson> lessonsList) {
        Map<String, List<Lesson>> lessonsByDay;
        List<ScheduleItem> scheduleItems = new ArrayList<>();

        lessonsByDay =  lessonsList.stream()
                .collect(Collectors.groupingBy(Lesson::getDate));

        for (Iterator<Map.Entry<String, List<Lesson>>> entries = lessonsByDay.entrySet().iterator(); entries.hasNext();){
            Map.Entry<String, List<Lesson>> entry = entries.next();
            ScheduleItemHeader header = new ScheduleItemHeader();
            header.setDate(entry.getKey());
            scheduleItems.add(header);

            List<Lesson> sortedLessons = sortLessons(entry.getValue());
            for(Lesson lesson : sortedLessons){
                ScheduleItem item = new ScheduleItem();
                item.setLessonStart(lesson.getBeginLesson());
                item.setLessonEnd(lesson.getEndLesson());
                item.setLessonType(lesson.getKindOfWork());
                item.setLessonName(lesson.getDiscipline());
                item.setTeacher(lesson.getLecturer());

                String place = lesson.getAuditorium() + ", " + lesson.getBuilding();
                item.setLessonPlace(place);

                scheduleItems.add(item);
            }
        }

        return scheduleItems;
    }

    private List<Lesson> sortLessons(List<Lesson> value) {
        return value;
    }
}
