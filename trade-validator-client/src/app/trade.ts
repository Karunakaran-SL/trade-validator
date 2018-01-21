export class Trade{
  public customer: string;
  public ccyPair = 'EURUSD';
  public type = 'Spot';
  public direction = 'BUY';
  public tradeDate: string;
  public amount1: number;
  public amount2: number;
  public rate: number;
  public valueDate: string;
  public legalEntity: string;
  public trader: string;
  public style = 'EUROPEAN';
  public strategy = 'CALL';
  public deliveryDate: string;
  public expiryDate: string;
  public excerciseStartDate: string;
  public payCcy: string;
  public premium: number;
  public premiumCcy: string;
  public premiumType: string;
  public premiumDate: string;
}
