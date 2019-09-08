package com.bupt.ZigbeeResolution.transform;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

public class OutBoundHandler extends ChannelOutboundHandlerAdapter {
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {		
		if (msg instanceof byte[]) {
			byte[] bytesWrite = (byte[])msg;
			ByteBuf buf = ctx.alloc().buffer(bytesWrite.length); 
			System.out.println("| Send to Gateway | " + SocketServer.bytesToHexString(bytesWrite));
			buf.writeBytes(bytesWrite); 
			ctx.writeAndFlush(buf).addListener(new ChannelFutureListener(){  
                @Override
                public void operationComplete(ChannelFuture future)  
                        throws Exception {  
                	System.out.println("operation complete!");
                }  
            });
		}
	}

//	public static void main(String[] args) {}
}