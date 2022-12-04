package org.hse.baseproject;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView start;
    private TextView finish;
    private TextView lessonName;
    private TextView lessonType;
    private TextView lessonClassroom;
    private TextView teacher;

    public ViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        start = itemView.findViewById(R.id.start_time);
        finish = itemView.findViewById(R.id.finish_time);
        lessonType = itemView.findViewById(R.id.lesson_type);
        lessonName = itemView.findViewById(R.id.lesson_name);
        lessonClassroom = itemView.findViewById(R.id.lesson_classroom);
        teacher = itemView.findViewById(R.id.lesson_teacher);
    }

    public void bind(final ScheduleItem lesson){
        start.setText(lesson.getLessonStart());
        finish.setText(lesson.getLessonEnd());
        lessonType.setText(lesson.getLessonType());
        lessonName.setText(lesson.getLessonName());
        teacher.setText(lesson.getTeacher());
        lessonClassroom.setText(lesson.getLessonPlace());
    }
}
