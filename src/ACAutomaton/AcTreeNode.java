package ACAutomaton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuwei
 * @Date: 2020/9/28
 * @desc: TODO
 */
public class AcTreeNode {
  public Character data;  //节点存放放入数据
  public List<Integer> exists;  //标志当前节点是不是一个匹配串的结尾
  public AcTreeNode fail;  //fail指针
  public Map<Character, AcTreeNode> subNodeMap;  //子节点
  public boolean hasScaned = false;

  public AcTreeNode(){
    exists = new ArrayList<>();
    fail = null;
    subNodeMap = new HashMap<>();
  }

  public AcTreeNode(char data){
    this.data = data;
    exists = new ArrayList<>();
    fail = null;
    subNodeMap = new HashMap<>();
  }
}
