package robotserver;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RobotServerEncoder extends MessageToByteEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(msg instanceof String){
            out.writeInt(1);
            out.writeShort(((String) msg).length());
            for(int i = 0; i < ((String) msg).length(); i++){
                out.writeByte(((String) msg).charAt(i));
            }
        }
//        String connect = "Connected to Server on ROBOT!";
//        out.writeShort(connect.length());
//        for(int i = 0; i < connect.length(); i ++){
//            out.writeByte(connect.charAt(i));
//        }
    }
}
