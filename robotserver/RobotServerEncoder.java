package robotserver;

import java.awt.image.BufferedImage;

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
        if(msg instanceof BufferedImage){
        	out.writeInt(2);
        	out.writeShort(((BufferedImage) msg).getWidth());
        	out.writeShort(((BufferedImage) msg).getHeight());
        	out.writeInt(((BufferedImage) msg).getType());
        	int pixels[] = new int[((BufferedImage) msg).getWidth()*((BufferedImage) msg).getHeight()];
    		((BufferedImage) msg).getRGB(0, 0, ((BufferedImage) msg).getWidth(), ((BufferedImage) msg).getHeight(), pixels, 0, ((BufferedImage) msg).getWidth());
    		for(int pixel : pixels){
    			out.writeInt(pixel);
    		}
        }
//        String connect = "Connected to Server on ROBOT!";
//        out.writeShort(connect.length());
//        for(int i = 0; i < connect.length(); i ++){
//            out.writeByte(connect.charAt(i));
//        }
    }
}
