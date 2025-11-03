package com.javasl.restwithjunittests.errors;

public class ResourceNotFoundException extends RuntimeException {

  private final String resourceName;
  private final String id;

  public ResourceNotFoundException(String resourceName, String id) {
    super();
    this.resourceName = resourceName;
    this.id = id;
  }

  public String getResourceName() {
    return resourceName;
  }

  public String getId() {
    return id;
  }
}
