package com.example.demo.springDupper;

/**
 * Интерфейс, имя которого заканчивается на MBean, затем можно использовать через JMX console.
 * Для этого запускаем jvisualvm из папки jdk/bin/, устанавливаем плагин VisualVM-MBeans,
 * открываем наш запущенный процесс в JVM, переходим во вкладку MBeans, далее заходим в папку домена,
 * который указали при регистрации MBean в ManagementFactory.getPlatformMBeanServer().registerMBean(),
 * и выбираем ключ, который также там указывали.
 * Теперь в режиме рантайма в самой JVM есть возможность изменять переменную enabled.
 */
public interface ProfilingControllerMBean {
	void setEnabled(boolean enabled);
}
