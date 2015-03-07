package com.ibatis.scorecardmodel.bo;

/** @author Stanislav Valenta, 28.4.2010 */
public class ResponseImpl<D, S> implements Response<D, S> {
  private static final long serialVersionUID = -2814204253289508442L;
  // requested data
  private D data;
  // response status
  private S status;

  // java bean convetion
  public ResponseImpl() {
  }

  /**
   * creates new instance based on passed params
   * @param data requested data
   * @param status response status
   */
  public ResponseImpl(D data, S status) {
    this.data = data;
    this.status = status;
  }

  /** @inheritDoc */
  @Override
  public D getData() {
    return data;
  }

  /** @inheritDoc */
  @Override
  public S getStatus() {
    return status;
  }

  /** data setter */
  public void setData(D data) {
    this.data = data;
  }

  /** status setter */
  public void setStatus(S status) {
    this.status = status;
  }
}
