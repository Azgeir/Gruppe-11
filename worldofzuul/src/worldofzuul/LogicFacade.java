/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;
import Acquaintance.*;
/**
 *
 * @author Simon
 */
public class LogicFacade implements ILogicFacade{
    
    private IDataFacade data;
    
    @Override
    public void injectData(IDataFacade data) {
        this.data = data;
    }
    
}
