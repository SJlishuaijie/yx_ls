package cn.baizhi.util;

import java.util.Arrays;

/**
 * 
 * @author others
 * date:2014-8-26  ����2:17:19
 * ��������������֤��ֵ����֤�봮
 */
public class SecurityCode {
	/**
	 * ��֤���Ѷȼ���
	 *   Simple-����
	 *   Medium-���ֺ�Сд��ĸ 
	 *   Hard-���ֺʹ�Сд��ĸ
	 */
	public enum SecurityCodeLevel {
		Simple, Medium, Hard
	};
	/**
	 * ����Ĭ����֤�룬4λ�е��Ѷ�
	 *
	 * @return
	 */
	public static String getSecurityCode() {
		return getSecurityCode(4, SecurityCodeLevel.Simple, false);
	}
	/**
	 * ����Ⱥ��Ѷ��������֤��
	 *
	 * @param length
	 * @param level
	 * @param isCanRepeat
	 * @return
	 */
	private static String getSecurityCode(int length, SecurityCodeLevel level, boolean isCanRepeat) {
		// ����ȡlen���ַ�
		int len = length;
		// �ַ�ϣ�--��ȥ�׻��������0,1,��ĸl,o,O��
		char[] codes = {
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
		};
		// ��ݲ�ͬ�ѶȽ�ȡ�ַ�
		if (level == SecurityCodeLevel.Simple) {
			codes = Arrays.copyOfRange(codes, 0, 10);
		} else if (level == SecurityCodeLevel.Medium) {
			codes = Arrays.copyOfRange(codes, 0, 36);
		}
		// �ַ�ͳ���
		int n = codes.length;
		// �׳�����ʱ�쳣
		if (len > n && isCanRepeat == false) {
			throw new RuntimeException(String.format("����SecurityCode.getSecurityCode(%1$s,%2$s,%3$s)�����쳣��" + "��isCanRepeatΪ%3$sʱ���������%1$s���ܴ���%4$s", len, level, isCanRepeat, n));
		}
		// ��ų�ȡ�������ַ�
		char[] result = new char[len];
		// �ж��ܷ�����ظ��ַ�
		if (isCanRepeat) {
			for (int i = 0; i < result.length; i++) {
				// ����0 and n-1
				int r = (int) (Math.random() * n);
				// ��result�еĵ�i��Ԫ������Ϊcode[r]��ŵ���ֵ
				result[i] = codes[r];
			}
		} else {
			for (int i = 0; i < result.length; i++) {
				// ����0 and n-1
				int r = (int) (Math.random() * n);
				// ��result�еĵ�i��Ԫ������Ϊcode[r]��ŵ���ֵ
				result[i] = codes[r];
				// ����ȷ�������ٴγ�ȡ���Ǹ��ַ����������������һ���ַ��дcode[r],����n-1
				codes[r] = codes[n - 1];
				n--;
			}
		}
		return String.valueOf(result);
	}
	public static void main(String[] args) {
		System.out.println(SecurityCode.getSecurityCode());
	}
}