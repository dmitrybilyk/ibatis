package rest.api;

/**
 * Created by dmitry on 28.12.14.
 */
public final class EnumFileNameType extends Enumable {
    private static final long serialVersionUID = 7801135636934953436L;
    public static final transient EnumFileNameType MAG = new EnumFileNameType("MAG");
    public static final transient EnumFileNameType AUDIO = new EnumFileNameType("AUDIO");
    public static final transient EnumFileNameType ARCHIVE = new EnumFileNameType("ARCHIVE");
    public static final transient EnumFileNameType VIDEO = new EnumFileNameType("VIDEO");
    public static final transient EnumFileNameType IMAGE = new EnumFileNameType("IMAGE");
    public static final transient EnumFileNameType RECD = new EnumFileNameType("RECD");

    private EnumFileNameType(String name) {
        super(name);
    }

    public static EnumFileNameType getEnum(String name) {
        return (EnumFileNameType)getEnum(name, EnumFileNameType.class);
    }
}

