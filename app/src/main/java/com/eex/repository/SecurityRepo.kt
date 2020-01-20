package com.eex.repository

import com.eex.domin.entity.security.Security
import io.reactivex.Observable

interface SecurityRepo {
  fun checkAuthStatus(): Observable<Security>
  fun getTradingType(): Observable<Security>
  fun getTradingNick(): Observable<Security>
}