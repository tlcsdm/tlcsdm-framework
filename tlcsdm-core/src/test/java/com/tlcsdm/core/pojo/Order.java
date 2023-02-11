/*
 * Copyright (c) 2019, 2023 unknowIfGuestInDream
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of unknowIfGuestInDream, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UNKNOWIFGUESTINDREAM BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.tlcsdm.core.pojo;

import java.util.Date;

public class Order {
    private String orderID;

    private String userName;
    private String commodityID;
    private Double totalPrices;
    private Integer count;
    private Date orderDate = new Date();

    public String getOrderID() {
        return orderID;
    }

    public Double getTotalPrices() {
        return totalPrices;
    }

    public Order setTotalPrices(Double totalPrices) {
        this.totalPrices = totalPrices;
        return this;
    }

    public Order setOrderID(String orderID) {
        this.orderID = orderID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public Order setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getCommodityID() {
        return commodityID;
    }

    public Order setCommodityID(String commodityID) {
        this.commodityID = commodityID;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public Order setCount(Integer count) {
        this.count = count;
        return this;
    }
}
