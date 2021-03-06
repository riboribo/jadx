package jadx.tests.integration.conditions;

import jadx.core.dex.instructions.args.InsnArg;
import jadx.core.dex.instructions.args.Named;
import jadx.core.dex.instructions.args.RegisterArg;
import jadx.core.dex.nodes.ClassNode;
import jadx.tests.api.IntegrationTest;

import org.junit.Test;

import static jadx.tests.api.utils.JadxMatchers.containsOne;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestTernary3 extends IntegrationTest {

	public static class TestCls {

		public boolean isNameEquals(InsnArg arg) {
			String n = getName(arg);
			if (n == null || !(arg instanceof Named)) {
				return false;
			}
			return n.equals(((Named) arg).getName());
		}

		private String getName(InsnArg arg) {
			if (arg instanceof RegisterArg) {
				return "r";
			}
			if (arg instanceof Named) {
				return "n";
			}
			return arg.toString();
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();

		// TODO:
		assertThat(code, containsOne("return (n == null || !(arg instanceof Named)) "
				+ "? false : n.equals(((Named) arg).getName());"));

		assertThat(code, not(containsString("if ((arg instanceof RegisterArg)) {")));
	}
}
