package application.models;

import javafx.scene.paint.Color;

import java.time.LocalTime;

public class Task implements Comparable<Task> {
    private LocalTime startTime;
    private LocalTime endTime;
    private Color color;
    private String location;
    private String title;

    public Task(LocalTime startTime, LocalTime endTime, Color color, String location, String title) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.color = color;
        this.location = location;
        this.title = title;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int compareTo(Task other) {
        return this.startTime.compareTo(other.startTime);
    }

	public String getLocation() {
		return this.location;
	}

	public String getDescription() {
		return this.title;
	}
}
