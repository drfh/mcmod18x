package ruby.bamboo.proxy;

import ruby.bamboo.core.Constants;
import ruby.bamboo.core.DataLoader;

/**
 * サーバープロクシ
 * 
 * @author Ruby
 * 
 */
public class CommonProxy {
	public void preInit() {
		try {
			DataLoader loader = new DataLoader();
			loader.init(Constants.BLOCK_PACKAGE);
			loader.init(Constants.ITEM_PACKAGE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void init() {

	}

	private void searchBlock() {

	}
}
