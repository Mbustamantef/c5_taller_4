package dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "Respuesta estándar de la API")
public class ApiResponse<T> {

  @Schema(description = "Mensaje descriptivo de la respuesta", example = "Operación exitosa")
  private String message;

  @Schema(description = "Código de estado HTTP", example = "200")
  private int code;

  @Schema(description = "Datos de la respuesta")
  private T data;

  public ApiResponse() {
  }

  public ApiResponse(String message, int code, T data) {
    this.message = message;
    this.code = code;
    this.data = data;
  }

  public static <T> ApiResponse<T> success(String message, T data) {
    return new ApiResponse<>(message, 200, data);
  }

  public static <T> ApiResponse<T> created(String message, T data) {
    return new ApiResponse<>(message, 201, data);
  }

  public static <T> ApiResponse<T> error(String message, int code) {
    return new ApiResponse<>(message, code, null);
  }

  public static <T> ApiResponse<T> notFound(String message) {
    return new ApiResponse<>(message, 404, null);
  }

  public static <T> ApiResponse<T> badRequest(String message) {
    return new ApiResponse<>(message, 400, null);
  }

  // Getters and Setters
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}

