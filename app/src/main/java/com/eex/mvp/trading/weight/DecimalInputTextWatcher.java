package com.eex.mvp.trading.weight;

import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * . ' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * <p>
 * .............................................
 * 佛祖保佑 永无BUG
 *
 * @ProjectName: EEX
 * @Package: com.eex.mvp.trading.weight
 * @ClassName: DecimalInputTextWatcher
 * @Description: java类作用描述
 * @Author: hcj
 * @CreateDate: 2020/1/6 11:52
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/1/6 11:52
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */

public class DecimalInputTextWatcher implements TextWatcher {

  /**
   * 默认  小数的位数   2 位
   */
  private static final int DEFAULT_DECIMAL_DIGITS = 2;

  private EditText editText;
  /**
   * 小数的位数
   */
  private int decimalDigits;
  /**
   * 整数的位数
   */
  private int integerDigits;

  public DecimalInputTextWatcher(EditText editText) {
    this.editText = editText;
    this.decimalDigits = DEFAULT_DECIMAL_DIGITS;
  }

  public DecimalInputTextWatcher(EditText editText, int decimalDigits) {
    this.editText = editText;
    if (decimalDigits <= 0)
      throw new RuntimeException("decimalDigits must > 0");
    this.decimalDigits = decimalDigits;
  }

  public DecimalInputTextWatcher(EditText editText, int integerDigits, int decimalDigits) {
    this.editText = editText;
    if (integerDigits <= 0)
      throw new RuntimeException("integerDigits must > 0");
    if (decimalDigits <= 0)
      throw new RuntimeException("decimalDigits must > 0");
    this.decimalDigits = decimalDigits;
    this.integerDigits = integerDigits;
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

  }

  @Override
  public void afterTextChanged(Editable editable) {
    String s = editable.toString();
    editText.removeTextChangedListener(this);

    if (s.contains(".")) {
      if (integerDigits > 0) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + decimalDigits + 1)});
      }
      if (s.length() - 1 - s.indexOf(".") > decimalDigits) {
        s = s.substring(0,
            s.indexOf(".") + decimalDigits + 1);
        //不输入超出位数的数字
        editable.replace(0, editable.length(), s.trim());
      }
    } else {
      if (integerDigits > 0) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + 1)});
        if (s.length() > integerDigits) {
          s = s.substring(0, integerDigits);
          editable.replace(0, editable.length(), s.trim());
        }
      }

    }
    //小数点开头，小数点前补0
    if (s.trim().equals(".")) {
      s = "0" + s;
      editable.replace(0, editable.length(), s.trim());
    }
    //多个0开头，只输入一个0
    if (s.startsWith("0") && s.trim().length() > 1) {
      if (!s.substring(1, 2).equals(".")) {
        editable.replace(0, editable.length(), "0");
      }
    }
    editText.addTextChangedListener(this);
  }
}
