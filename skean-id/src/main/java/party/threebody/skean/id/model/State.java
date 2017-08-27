package party.threebody.skean.id.model;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum State {
	SUSPENDED('S'), TERMINATED('T'), ACTIVE('A'), INACTIVE('I');
	private char code;
	private static final Map<Character, State> codeMap = Arrays.stream(State.values())
			.collect(Collectors.toMap(x -> x.code, x -> x));

	private State(char code) {
		this.code = code;
	}

	public static State valueOf(char code){
		return codeMap.get(code);
	}
}