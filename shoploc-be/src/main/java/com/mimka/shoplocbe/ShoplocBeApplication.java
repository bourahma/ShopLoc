package com.mimka.shoplocbe;

import com.mimka.shoplocbe.batch.order.OrderDAO;
import com.mimka.shoplocbe.entities.*;
import com.mimka.shoplocbe.repositories.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@SpringBootApplication
public class ShoplocBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoplocBeApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner (CommerceRepository commerceRepository,
										 OrderRepository orderRepository,
										 OrderProductRepository orderProductRepository,
										 CustomerRepository customerRepository,
										 CustomerConnectionRepository customerConnectionRepository,
										 VfpHistoryRepository vfpHistoryRepository) {
		return args -> {

			// Customer connections
			/*for (Customer customer : customerRepository.findAll())
			{
				LocalDate subscriptionDate = customer.getSubscriptionDate();
				LocalDate today = LocalDate.now();

				Random random = new Random();

				while (subscriptionDate != null && !subscriptionDate.isAfter(today) && customer.getEnabled()) {
					int connected = random.nextInt(2);
					if (connected == 1) {
						CustomerConnection customerConnection = new CustomerConnection();
						customerConnection.setCustomer(customer);

						int hour = random.nextInt(24);
						int minute = random.nextInt(60);
						int second = random.nextInt(60);
						LocalDateTime connectTime = LocalDateTime.of(subscriptionDate, LocalTime.of(hour, minute, second));

						int durationInMinutes = random.nextInt(46);

						LocalDateTime disconnectTime = connectTime.plusMinutes(durationInMinutes);

						customerConnection.setConnectTime(connectTime);
						customerConnection.setDisconnectTime(disconnectTime);
						customerConnectionRepository.save(customerConnection);
					}
					subscriptionDate = subscriptionDate.plusDays(1);
				}
			}*/

			// Customer orders
			for (Customer customer : customerRepository.findAll()) {
				List<Commerce> commerces = commerceRepository.findAll();

				LocalDate customerSubscriptionDate = customer.getSubscriptionDate();
				LocalDate today = LocalDate.now();

				while (customerSubscriptionDate.isBefore(today) && customer.getEnabled()) {

					Random random = new Random();
					int o = random.nextInt(3);

					if (o == 2) {
						Commerce commerce = commerces.get(random.nextInt(commerces.size()));
						List<Product> commerceProducts = commerce.getProducts();
						Order order = new Order();
						order.setCustomer(customer);
						order.setOrderDate(customerSubscriptionDate);
						order.setOrderStatus(OrderStatus.PAID.name());
						order.setCommerce(commerce);
						order = orderRepository.save(order);

						if (!commerce.getProducts().isEmpty()) {
							Product product = commerceProducts.get(random.nextInt(commerceProducts.size()));
							OrderProduct orderProduct = new OrderProduct();
							orderProduct.setProduct(product);
							orderProduct.setOrder(order);

							OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());
							orderProduct.setId(orderProductId);

							if (product.getPrice() > 100.00) {
								orderProduct.setQuantity(random.nextInt(2) + 1);
							} else {
								orderProduct.setQuantity(random.nextInt(10) + 1);
							}
								//orderProduct.setPurchasePrice(product.getPrice());
							orderProduct.setPromotion(null);
							orderProductRepository.save(orderProduct);
						}
					} else if (o == 1 && random.nextInt(2) == 1) {
						Commerce commerce = commerces.get(random.nextInt(commerces.size()));
						List<Product> commerceProducts = commerce.getProducts();
						Order order = new Order();
						order.setCustomer(customer);
						order.setOrderDate(customerSubscriptionDate);
						order.setOrderStatus(OrderStatus.PAID.name());
						order.setCommerce(commerce);
						order = orderRepository.save(order);

						if (!commerce.getProducts().isEmpty()) {
							Product product = commerceProducts.get(random.nextInt(commerceProducts.size()));
							OrderProduct orderProduct = new OrderProduct();
							orderProduct.setProduct(product);
							orderProduct.setOrder(order);

							OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());
							orderProduct.setId(orderProductId);

							if (product.getPrice() > 100.00) {
								orderProduct.setQuantity(random.nextInt(2) + 1);
							} else {
								orderProduct.setQuantity(random.nextInt(10) + 1);
							}
							//orderProduct.setPurchasePrice(product.getPrice());
							orderProduct.setPromotion(null);
							orderProductRepository.save(orderProduct);
						}

						order = new Order();
						order.setCustomer(customer);
						order.setOrderDate(customerSubscriptionDate.plusDays(1));
						order.setOrderStatus(OrderStatus.PAID.name());
						order.setCommerce(commerce);
						order = orderRepository.save(order);

						if (!commerce.getProducts().isEmpty()) {
							Product product = commerceProducts.get(random.nextInt(commerceProducts.size()));
							OrderProduct orderProduct = new OrderProduct();
							orderProduct.setProduct(product);
							orderProduct.setOrder(order);

							OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());
							orderProduct.setId(orderProductId);

							if (product.getPrice() > 100.00) {
								orderProduct.setQuantity(random.nextInt(2) + 1);
							} else {
								orderProduct.setQuantity(random.nextInt(10) + 1);
							}
							//orderProduct.setPurchasePrice(product.getPrice());
							orderProduct.setPromotion(null);
							orderProductRepository.save(orderProduct);
						}

						order = new Order();
						order.setCustomer(customer);
						order.setOrderDate(customerSubscriptionDate.plusDays(2));
						order.setOrderStatus(OrderStatus.PAID.name());
						order.setCommerce(commerce);
						order = orderRepository.save(order);

						if (!commerce.getProducts().isEmpty()) {
							Product product = commerceProducts.get(random.nextInt(commerceProducts.size()));
							OrderProduct orderProduct = new OrderProduct();
							orderProduct.setProduct(product);
							orderProduct.setOrder(order);

							OrderProductId orderProductId = new OrderProductId(order.getOrderId(), product.getProductId());
							orderProduct.setId(orderProductId);

							if (product.getPrice() > 100.00) {
								orderProduct.setQuantity(random.nextInt(2) + 1);
							} else {
								orderProduct.setQuantity(random.nextInt(7) + 1);
							}
							//orderProduct.setPurchasePrice(product.getPrice());
							orderProduct.setPromotion(null);
							orderProductRepository.save(orderProduct);
						}

						VfpHistory vfpHistory = new VfpHistory();
						vfpHistory.setGrantedDate(customerSubscriptionDate.plusDays(3));
						vfpHistory.setCustomer(customer);
						vfpHistory.setExpirationDate(customerSubscriptionDate.plusDays(10));
						vfpHistoryRepository.save(vfpHistory);
					}
					customerSubscriptionDate = customerSubscriptionDate.plusDays(7);
				}
			}
		};
	}
}
