package jp.thinq.solr.normalization;

import java.text.Normalizer;
import java.util.Map;

import org.apache.lucene.analysis.CharStream;
import org.apache.solr.analysis.BaseCharFilterFactory;

public class NormalizeCharFilterFactory extends BaseCharFilterFactory {

	static Normalizer.Form form = null;
	
	@Override
	public CharStream create(CharStream input) {
	    return new NormalizeCharFilter(form, input);
	}
	@Override
	public void init(Map<String, String> args) {
		super.init(args);
		setup_normalization_form();
	}

	private void setup_normalization_form() {
		if (form != null) return;
		String nf = args.get("norm");
		if (nf == null) {
			form = Normalizer.Form.NFKC;
			return;
		}
		nf = nf.toUpperCase();
		if (nf.equals("NFD")) {
			form = Normalizer.Form.NFD;
		} else if (nf.equals("NFC")) {
			form = Normalizer.Form.NFC;
		} else if (nf.equals("NFKD")) {
			form = Normalizer.Form.NFKD;
		} else {
			form = Normalizer.Form.NFKC;
		}
	}

}
