//package nl.wehkamp.everest.proxy;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.handler.codec.http.HttpRequest;
//
//import org.littleshoot.proxy.HttpFilters;
//import org.littleshoot.proxy.HttpFiltersAdapter;
//import org.littleshoot.proxy.HttpFiltersSourceAdapter;
//import org.littleshoot.proxy.HttpProxyServer;
//import org.littleshoot.proxy.impl.DefaultHttpProxyServer;
//
//public class Proxy {
//	private int port = 8080;
//	private HttpProxyServer server;
//
//	public void start() {
//		server = DefaultHttpProxyServer.bootstrap().withPort(port).withFiltersSource(new HttpFiltersSourceAdapter() {
//			public HttpFilters filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
//				return new HttpFiltersAdapter(originalRequest) {
//
//				};
//			}
//		}).start();
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}
// }
