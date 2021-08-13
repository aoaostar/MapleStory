package handling;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import tools.HexTool;

public class ExternalCodeTableGetter {
    Properties props;

    public ExternalCodeTableGetter(final Properties properties) {
        this.props = properties;
    }

    private static  String valueOf(String name, String[] values) {
        for (String val : values) {
            if (val.equals(name)) {
                return val;
            }
        }
        return null;
    }
    private static <T extends Enum> T valueOf(String name, T[] values) {
        for (T val : values) {
            if (val.name().equals(name)) {
                return val;
            }
        }
        return null;
    }


    private <T extends Enum<? extends WritableIntValueHolder> & WritableIntValueHolder> short getValue(String name, T[] values, short def) {
        String prop = this.props.getProperty(name);
        if (prop != null && prop.length() > 0) {
            String trimmed = prop.trim();
            String[] args = trimmed.split(" ");
            int base = 0;
            String offset;
            if (args.length == 2) {
                base = valueOf(args[0], values).getValue();
                if (base == def) {
                    base = this.getValue(args[0], values, def);
                }

                offset = args[1];
            } else {
                offset = args[0];
            }

            return offset.length() > 2 && offset.substring(0, 2).equals("0x") ? (short) (Short.parseShort(offset.substring(2), 16) + base) : (short) (Short.parseShort(offset) + base);
        } else {
            return def;
        }
    }

    public static <T extends Enum<? extends WritableIntValueHolder> & WritableIntValueHolder> String getOpcodeTable(T[] enumeration) {
        StringBuilder enumVals = new StringBuilder();
        List<T> all = new ArrayList<>();
        all.addAll(Arrays.asList(enumeration));
        Collections.sort(all, (Comparator) new Comparator<WritableIntValueHolder>() {
            public int compare(WritableIntValueHolder o1, WritableIntValueHolder o2) {
                return Short.valueOf(o1.getValue()).compareTo(Short.valueOf(o2.getValue()));
            }
        });
        for (Enum enum_ : all) {
            enumVals.append(enum_.name());
            enumVals.append(" = ");
            enumVals.append("0x");
            enumVals.append(HexTool.toString(((WritableIntValueHolder) enum_).getValue()));
            enumVals.append(" (");
            enumVals.append(((WritableIntValueHolder) enum_).getValue());
            enumVals.append(")\n");
        }
        return enumVals.toString();
    }

    public static <T extends java.lang.Enum> void populateValues(Properties properties, T[] values) {
        ExternalCodeTableGetter exc = new ExternalCodeTableGetter(properties);
        for (T code : values) {
            if(code instanceof SendPacketOpcode) {
                SendPacketOpcode[] new_values = (SendPacketOpcode[]) values;
                ((WritableIntValueHolder) code).setValue( exc.getValue(code.name(),new_values,(short)-2));
            }
            if(code instanceof RecvPacketOpcode) {
                RecvPacketOpcode[] new_values = (RecvPacketOpcode[]) values;
                ((WritableIntValueHolder) code).setValue(exc.getValue(code.name(), new_values, (short) (-2)));
            }

//            ((WritableIntValueHolder) code).setValue(exc.getValue(code.name(), values, (short) (-2)));
        }
    }
}
