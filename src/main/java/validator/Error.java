package validator;

public final class Error {
    private final String code;
    private final String message;

    private Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Error of(String code, String message) {
        return new Error(code, message);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Error)) return false;
        final Error other = (Error) o;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "Error(code=" + this.getCode() + ", message=" + this.getMessage() + ")";
    }
}
