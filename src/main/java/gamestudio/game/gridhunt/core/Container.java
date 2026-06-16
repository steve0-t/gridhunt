package gamestudio.game.gridhunt.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Container<T> {
  @JsonCreator
  public Container(@JsonProperty("elements") T[] elements) {
    this.elements = elements;
    maxElements = elements.length;
    activeIndex = 0;
  }

  public boolean initElement(int index, T element) {
    if (index < 0 || index >= maxElements) return false;
    elements[index] = element;
    activeIndex = index;
    return true;
  }

  public boolean setActiveElement(int index, T element) {
    if (index < 0 || index >= maxElements) return false;
    if (elements[index] == null) {
      if (initElement(index, element)) return false;
    }
    activeIndex = index;
    return true;
  }

  public T getActive() {
    return elements[activeIndex];
  }

  public void reset() {
    for (int i = 0; i < maxElements; i++) {
      elements[i] = null;
    }
    activeIndex = 0;
  }

  public int getMaxElements() {
    return maxElements;
  }

  public T[] getElements() {
    return elements.clone();
  }

  public int getActiveIndex() {
    return activeIndex;
  }

  private final T[] elements;
  private int activeIndex;
  private final int maxElements;
}
