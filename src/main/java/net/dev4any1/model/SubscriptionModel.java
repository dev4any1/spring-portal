package net.dev4any1.model;

import net.dev4any1.pojo.Subscription;

public class SubscriptionModel extends Subscription implements DbObject {
		private Long id;

		@Override
		public Long getId() {
			return id;
		}

		@Override
		public void setId(Long id) {
			this.id = id;	
		}

		

		
}
