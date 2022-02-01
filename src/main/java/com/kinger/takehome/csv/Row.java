package com.kinger.takehome.csv;

import java.util.Map;

public record Row(Map<Integer, String> line, String rowLine) {
}
